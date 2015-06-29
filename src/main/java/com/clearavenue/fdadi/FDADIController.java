/*
 *
 */
package com.clearavenue.fdadi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

/**
 * The Class FDADIController.
 */
@Controller
public class FDADIController {

	/** The Constant logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FDADIController.class);

	/** The Constant mongo. */
	private static final Datastore MONGO = MongoDB.instance().getDatabase();

	/** The Constant userDAO. */
	private static final UserProfileDAO USER_DAO = new UserProfileDAO(MONGO);

	/** The Constant allmedDAO. */
	private static final AllMedicationsDAO ALLMED_DAO = new AllMedicationsDAO(MONGO);

	/** The Constant allpharmDAO. */
	private static final AllPharmClassesDAO ALLPHARM_DAO = new AllPharmClassesDAO(MONGO);

	/** The err msg. */
	private String errMsg = "";

	/**
	 * Index.
	 *
	 * @param req
	 *            the req
	 * @param map
	 *            the map
	 * @return the string
	 * @throws UnirestException
	 *             the unirest exception
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public final String index(final HttpServletRequest req, final ModelMap map) throws UnirestException {
		// check if logged in and if not redirect to login
		final HttpSession session = req.getSession();
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:/login";
		}

		final UserProfile user = USER_DAO.findByUserId(loggedInUsername);
		final List<UserMedication> medications = user.getMedications();
		Collections.sort(medications);
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
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Adding [" + med + "] to recall list.");
					}
				}
			}
			map.addAttribute("recallList", recalls);

			session.setAttribute("showAlert", "false");
		}
		map.addAttribute("showAlert", showAlert);
		return "index";
	}

	/**
	 * Faq.
	 *
	 * @param req
	 *            the req
	 * @param map
	 *            the map
	 * @return the string
	 */
	@RequestMapping(value = "/faq", method = RequestMethod.GET)
	public final String faq(final HttpServletRequest req, final ModelMap map) {
		final String loggedInUsername = (String) req.getSession().getAttribute("username");
		map.addAttribute("loggedIn", !StringUtils.isBlank(loggedInUsername));
		return "faq";
	}

	/**
	 * Login.
	 *
	 * @param map
	 *            the map
	 * @return the string
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public final String login(final ModelMap map) {
		if (StringUtils.isNotEmpty(errMsg)) {
			map.addAttribute("errMsg", errMsg);
		}

		return "login";
	}

	/**
	 * Process login.
	 *
	 * @param req
	 *            the req
	 * @param map
	 *            the map
	 * @return the string
	 */
	@RequestMapping(value = "/processLogin", method = RequestMethod.POST)
	public final String processLogin(final HttpServletRequest req, final ModelMap map) {
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

	/**
	 * Register user.
	 *
	 * @param req
	 *            the req
	 * @param map
	 *            the map
	 * @return the string
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public final String registerUser(final HttpServletRequest req, final ModelMap map) {
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

	/**
	 * Adds the med by name.
	 *
	 * @param req
	 *            the req
	 * @param map
	 *            the map
	 * @return the string
	 */
	@RequestMapping(value = "/addMedByName", method = RequestMethod.GET)
	public final String addMedByName(final HttpServletRequest req, final ModelMap map) {
		// check if logged in and if not redirect to login
		final HttpSession session = req.getSession();
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:/login";
		}

		final QueryResults<AllMedications> all = ALLMED_DAO.find();
		map.addAttribute("allMeds", all.asList().get(0).getMedicationNames());

		return "addMedByName";
	}

	/**
	 * Process add med by name.
	 *
	 * @param req
	 *            the req
	 * @param map
	 *            the map
	 * @return the string
	 */
	@RequestMapping(value = "/processAddMedByName", method = RequestMethod.POST)
	public final String processAddMedByName(final HttpServletRequest req, final ModelMap map) {
		// check if logged in and if not redirect to login
		final HttpSession session = req.getSession();
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:/login";
		}

		final UserProfile user = USER_DAO.findByUserId(loggedInUsername);

		final String medParam = req.getParameter("meds");
		final String[] meds = medParam.split("---,---");

		for (final String medication : meds) {
			USER_DAO.addUserMedication(user, new UserMedication(medication));
		}

		return "redirect:/";
	}

	/**
	 * Adds the med by pharm class.
	 *
	 * @param req
	 *            the req
	 * @param map
	 *            the map
	 * @return the string
	 */
	@RequestMapping(value = "/addMedByPharmClass", method = RequestMethod.GET)
	public final String addMedByPharmClass(final HttpServletRequest req, final ModelMap map) {
		// check if logged in and if not redirect to login
		final HttpSession session = req.getSession();
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:/login";
		}

		final QueryResults<AllPharmClasses> all = ALLPHARM_DAO.find();
		map.addAttribute("allPharmClasses", all.asList().get(0).getPharmClassNames());

		return "addMedByPharmClass";
	}

	/**
	 * Process add med by pharm class.
	 *
	 * @param req
	 *            the req
	 * @param map
	 *            the map
	 * @return the string
	 * @throws UnirestException
	 *             the unirest exception
	 */
	@RequestMapping(value = "/processAddMedByPharmClass", method = RequestMethod.POST)
	public final String processAddMedByPharmClass(final HttpServletRequest req, final ModelMap map) throws UnirestException {
		// check if logged in and if not redirect to login
		final HttpSession session = req.getSession();
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:/login";
		}

		final String pharmClassesParam = req.getParameter("pharmclasses");
		final String[] pharmClasses = pharmClassesParam.split("---,---");

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

	/**
	 * Med details.
	 *
	 * @param req
	 *            the req
	 * @param map
	 *            the map
	 * @return the string
	 */
	@RequestMapping(value = "/medDetails", method = RequestMethod.POST)
	public final String medDetails(final HttpServletRequest req, final ModelMap map) {
		// check if logged in and if not redirect to login
		final HttpSession session = req.getSession();
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:/login";
		}

		final String medlist = StringUtils.defaultString(req.getParameter("medlist"));
		final String[] drugNames = medlist.split("---,---");
		final List<Drug> drugs = Drug.getDrugs(drugNames);
		map.addAttribute("medList", drugs);

		final String[] attributes = { "showSideEffects", "showUsage", "showIndications", "showInteractions", "showCounterindications" };
		for (final String s : attributes) {
			map.addAttribute(s, StringUtils.defaultString(req.getParameter(s), "false"));
		}
		return "medDetails";
	}

	/**
	 * Recalls.
	 *
	 * @param req
	 *            the req
	 * @param map
	 *            the map
	 * @return the string
	 * @throws UnirestException
	 *             the unirest exception
	 */
	@RequestMapping(value = "/recalls", method = RequestMethod.POST)
	public final String recalls(final HttpServletRequest req, final ModelMap map) throws UnirestException {
		final String medlist = StringUtils.defaultString(req.getParameter("medlist"));
		final String[] drugNames = medlist.split("---,---");
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

	/**
	 * Removes the meds.
	 *
	 * @param req
	 *            the req
	 * @param map
	 *            the map
	 * @return the string
	 */
	@RequestMapping(value = "/removeMeds", method = RequestMethod.POST)
	public final String removeMeds(final HttpServletRequest req, final ModelMap map) {
		// check if logged in and if not redirect to login
		final HttpSession session = req.getSession();
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:/login";
		}

		final String medlist = StringUtils.defaultString(req.getParameter("medlist"));
		final String[] drugNames = medlist.split("---,---");

		final UserProfile user = USER_DAO.findByUserId(loggedInUsername);
		for (final String drugName : drugNames) {
			USER_DAO.deleteUserMedication(user, new UserMedication(drugName));
		}

		return "redirect:/";
	}

	/**
	 * Check interactions.
	 *
	 * @param req
	 *            the req
	 * @param res
	 *            the res
	 * @return the string
	 * @throws UnirestException
	 *             the unirest exception
	 */
	@RequestMapping(value = "/checkInteractions", method = RequestMethod.POST)
	@ResponseBody
	public final String checkInteractions(final HttpServletRequest req, final HttpServletResponse res) throws UnirestException {
		final String medlist = StringUtils.defaultString(req.getParameter("medList"));
		final String[] drugNames = medlist.split("---,---");
		final List<String> drugs = Arrays.asList(drugNames);

		final HashMap<String, ArrayList<String>> interactions = DrugInteractions.findInteractions(drugs);
		return Boolean.toString(interactions.keySet().size() > 0);

	}

	/**
	 * Logout.
	 *
	 * @param req
	 *            the req
	 * @param map
	 *            the map
	 * @return the string
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public final String logout(final HttpServletRequest req, final ModelMap map) {
		final HttpSession session = req.getSession();
		session.removeAttribute("username");
		return "redirect:/";
	}

	/**
	 * Register.
	 *
	 * @param username
	 *            the username
	 * @param pwd
	 *            the pwd
	 * @return true, if successful
	 */
	private boolean register(final String username, final String pwd) {
		boolean result = false;
		if (USER_DAO.save(new UserProfile(username, pwd)) != null) {
			final UserProfile user = USER_DAO.findByUserId(username);
			result = user.getUserId().equals(username);
		}
		return result;
	}

	/**
	 * Validate.
	 *
	 * @param username
	 *            the username
	 * @param pwd
	 *            the pwd
	 * @return true, if successful
	 */
	private boolean validate(final String username, final String pwd) {
		boolean result = false;
		final UserProfile user = USER_DAO.findByUserId(username);
		if (user != null) {
			result = user.getPassword().equals(pwd);
		}
		return result;
	}

	/**
	 * Clean up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@PreDestroy
	public final void cleanUp() throws Exception {
		LOGGER.info("Disconnecting MongoDB");
		MongoDB.instance().close();
	}
}
