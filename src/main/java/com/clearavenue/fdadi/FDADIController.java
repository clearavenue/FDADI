package com.clearavenue.fdadi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.QueryResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clearavenue.data.AllMedicationsDAO;
import com.clearavenue.data.AllPharmClassesDAO;
import com.clearavenue.data.MongoDB;
import com.clearavenue.data.UserProfileDAO;
import com.clearavenue.data.objects.AllMedications;
import com.clearavenue.data.objects.AllPharmClasses;
import com.clearavenue.data.objects.UserMedication;
import com.clearavenue.data.objects.UserProfile;
import com.clearavenue.fdadi.api.ApiQueries;
import com.clearavenue.fdadi.api.Drug;
import com.clearavenue.fdadi.api.DrugInteractions;
import com.clearavenue.fdadi.api.RecallEvent;
import com.mashape.unirest.http.exceptions.UnirestException;

@Controller
public class FDADIController {

	private static final Logger logger = LoggerFactory.getLogger(FDADIController.class);
	private static final Datastore mongo = MongoDB.instance().getDatabase();
	private static final UserProfileDAO userDAO = new UserProfileDAO(mongo);
	private static final AllMedicationsDAO allmedDAO = new AllMedicationsDAO(mongo);
	private static final AllPharmClassesDAO allpharmDAO = new AllPharmClassesDAO(mongo);

	public String errMsg = "";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest req, final ModelMap map) throws UnirestException {
		// check if logged in and if not redirect to login
		final HttpSession session = req.getSession();
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:/login";
		}

		final UserProfile user = userDAO.findByUserId(loggedInUsername);
		final List<UserMedication> medications = user.getMedications();
		map.addAttribute("medList", medications);

		final boolean showAlert = Boolean.parseBoolean(StringUtils.defaultString((String) session.getAttribute("showAlert"), "true"));
		if (showAlert) {
			final List<String> medList = new ArrayList<String>();
			for (final UserMedication med : medications) {
				medList.add(med.getMedicationName());
			}
			final List<String> interactions = new ArrayList<String>(DrugInteractions.findInteractions(medList).keySet());
			map.addAttribute("interactionList", interactions);
			final List<String> recalls = new ArrayList<String>();
			for (final String med : medList) {
				final List<RecallEvent> recallList = ApiQueries.getRecallStatus(med, 1);
				if (recallList.size() > 0) {
					recalls.add(med);
					logger.debug("Adding [" + med + "] to recall list.");
				}
			}
			map.addAttribute("recallList", recalls);

			session.setAttribute("showAlert", "false");
		}
		map.addAttribute("showAlert", showAlert);
		return "index";
	}

	@RequestMapping(value = "/faq", method = RequestMethod.GET)
	public String faq() {
		return "faq";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(final ModelMap map) {
		if (StringUtils.isNotEmpty(errMsg)) {
			map.addAttribute("errMsg", errMsg);
		}

		return "login";
	}

	@RequestMapping(value = "/processLogin", method = RequestMethod.POST)
	public String processLogin(HttpServletRequest req, final ModelMap map) {
		final String username = StringUtils.defaultString(req.getParameter("username"));
		final String pwd = StringUtils.defaultString(req.getParameter("pwd"));

		String view;
		if (validate(username, pwd)) {
			final HttpSession session = req.getSession();
			session.setAttribute("username", username);

			view = "redirect:/";
		} else {
			errMsg = "Invalid login/password";
			view = "redirect:/login?loginError";
		}

		req.getSession().setAttribute("showAlert", "true");
		return view;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(HttpServletRequest req, final ModelMap map) {
		final String username = StringUtils.defaultString(req.getParameter("username"));
		final String pwd = StringUtils.defaultString(req.getParameter("pwd"));

		String view;
		if (register(username, pwd)) {
			final HttpSession session = req.getSession();
			session.setAttribute("username", username);
			view = "redirect:/";
		} else {
			errMsg = "Registration failed: Username is already taken.";
			view = "redirect:/login?loginError";
		}

		return view;
	}

	@RequestMapping(value = "/addMedByName", method = RequestMethod.GET)
	public String addMedByName(HttpServletRequest req, final ModelMap map) {
		// check if logged in and if not redirect to login
		final HttpSession session = req.getSession();
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:/login";
		}

		final QueryResults<AllMedications> all = allmedDAO.find();
		map.addAttribute("allMeds", all.asList().get(0).getMedicationNames());

		return "addMedByName";
	}

	@RequestMapping(value = "/processAddMedByName", method = RequestMethod.POST)
	public String processAddMedByName(HttpServletRequest req, final ModelMap map) {
		// check if logged in and if not redirect to login
		final HttpSession session = req.getSession();
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:/login";
		}

		final UserProfile user = userDAO.findByUserId(loggedInUsername);

		final String medParam = req.getParameter("meds");
		final String[] meds = medParam.split(",");

		for (final String medication : meds) {
			userDAO.addUserMedication(user, new UserMedication(medication));
		}

		return "redirect:/";
	}

	@RequestMapping(value = "/addMedByPharmClass", method = RequestMethod.GET)
	public String addMedByPharmClass(HttpServletRequest req, final ModelMap map) {
		// check if logged in and if not redirect to login
		final HttpSession session = req.getSession();
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:/login";
		}

		final QueryResults<AllPharmClasses> all = allpharmDAO.find();
		map.addAttribute("allPharmClasses", all.asList().get(0).getPharmClassNames());

		return "addMedByPharmClass";
	}

	@RequestMapping(value = "/processAddMedByPharmClass", method = RequestMethod.POST)
	public String processAddMedByPharmClass(HttpServletRequest req, final ModelMap map) throws UnirestException {
		// check if logged in and if not redirect to login
		final HttpSession session = req.getSession();
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:/login";
		}

		final String pharmClassesParam = req.getParameter("pharmclasses");
		final String[] pharmClasses = pharmClassesParam.split(",");

		final List<String> medications = new ArrayList<String>();

		for (final String pharmClass : pharmClasses) {
			final List<String> results = ApiQueries.findByPharmClass(pharmClass);
			if (!results.isEmpty()) {
				medications.addAll(results);
			}
		}

		map.addAttribute("allMeds", medications);

		return "addMedByName";
	}

	@RequestMapping(value = "/medDetails", method = RequestMethod.POST)
	public String medDetails(HttpServletRequest req, ModelMap map) {
		// check if logged in and if not redirect to login
		final HttpSession session = req.getSession();
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:/login";
		}

		final String medlist = StringUtils.defaultString(req.getParameter("medlist"));
		final String[] drugNames = medlist.split(",");
		final List<Drug> drugs = Drug.getDrugs(drugNames);
		map.addAttribute("medList", drugs);

		final String[] attributes = { "showSideEffects", "showUsage", "showIndications", "showInteractions", "showCounterindications" };
		for (final String s : attributes) {
			map.addAttribute(s, StringUtils.defaultString(req.getParameter(s), "false"));
		}
		return "medDetails";
	}

	@RequestMapping(value = "/recalls", method = RequestMethod.POST)
	public String recalls(HttpServletRequest req, ModelMap map) throws UnirestException {
		final String medlist = StringUtils.defaultString(req.getParameter("medlist"));
		final String[] drugNames = medlist.split(",");
		final List<List<RecallEvent>> list = new ArrayList<List<RecallEvent>>();
		for (final String drug : drugNames) {
			final List<RecallEvent> recalls = ApiQueries.getRecallStatus(drug);
			if (recalls.size() > 0) {
				list.add(recalls);
			}
		}
		map.addAttribute("recallList", list);
		return "recalls";
	}

	@RequestMapping(value = "/removeMeds", method = RequestMethod.POST)
	public String removeMeds(HttpServletRequest req, ModelMap map) {
		// check if logged in and if not redirect to login
		final HttpSession session = req.getSession();
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:/login";
		}

		final String medlist = StringUtils.defaultString(req.getParameter("medlist"));
		final String[] drugNames = medlist.split(",");

		final UserProfile user = userDAO.findByUserId(loggedInUsername);
		for (final String drugName : drugNames) {
			userDAO.deleteUserMedication(user, new UserMedication(drugName));
		}

		return "redirect:/";
	}

	@RequestMapping(value = "/checkInteractions", method = RequestMethod.POST)
	@ResponseBody
	public String checkInteractions(HttpServletRequest req, HttpServletResponse res) throws UnirestException {
		final String medlist = StringUtils.defaultString(req.getParameter("medList"));
		final String[] drugNames = medlist.split(",");
		final List<String> drugs = Arrays.asList(drugNames);

		final HashMap<String, ArrayList<String>> interactions = DrugInteractions.findInteractions(drugs);
		return Boolean.toString(interactions.keySet().size() > 0);

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest req, ModelMap map) {
		final HttpSession session = req.getSession();
		session.removeAttribute("username");
		return "redirect:/";
	}

	private boolean register(String username, String pwd) {
		boolean result = false;
		if (userDAO.save(new UserProfile(username, pwd)) != null) {
			final UserProfile user = userDAO.findByUserId(username);
			result = user.getUserId().equals(username);
		}
		return result;
	}

	private boolean validate(String username, String pwd) {
		final UserProfile user = userDAO.findByUserId(username);
		return user == null ? false : user.getPassword().equals(pwd);
	}

	@PreDestroy
	public void cleanUp() throws Exception {
		logger.info("Disconnecting MongoDB");
		MongoDB.instance().close();
	}
}
