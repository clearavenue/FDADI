package com.clearavenue.fdadi;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
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

import com.clearavenue.data.AllMedicationsDAO;
import com.clearavenue.data.AllPharmClassesDAO;
import com.clearavenue.data.MongoDB;
import com.clearavenue.data.UserProfileDAO;
import com.clearavenue.data.objects.AllMedications;
import com.clearavenue.data.objects.AllPharmClasses;
import com.clearavenue.data.objects.UserMedication;
import com.clearavenue.data.objects.UserProfile;
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
	public String index(HttpServletRequest req, final ModelMap map) {
		HttpSession session = req.getSession();
		String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:/login";
		}

		UserProfile user = userDAO.findByUserId(loggedInUsername);
		List<UserMedication> medications = user.getMedications();
		map.addAttribute("medList", medications);

		return "index";
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
		String view;

		final String username = StringUtils.defaultString(req.getParameter("username"));
		final String pwd = StringUtils.defaultString(req.getParameter("pwd"));

		logger.info("processing Login");

		if (validate(username, pwd)) {
			logger.info("login passed");
			HttpSession session = req.getSession();
			session.setAttribute("username", username);

			view = "redirect:/";
		} else {
			logger.info("login failed");
			errMsg = "Invalid login/password";
			view = "redirect:/login?loginError";
		}

		return view;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(HttpServletRequest req, final ModelMap map) {
		String view;

		final String username = StringUtils.defaultString(req.getParameter("username"));
		final String pwd = StringUtils.defaultString(req.getParameter("pwd"));

		logger.info("registering Login");

		if (register(username, pwd)) {
			logger.info("register passed");
			HttpSession session = req.getSession();
			session.setAttribute("username", username);
			view = "redirect:/";
		} else {
			logger.info("register failed");
			errMsg = "Failed to register";
			view = "redirect:/login?loginError";
		}

		return view;
	}

	@RequestMapping(value = "/addMedByName", method = RequestMethod.GET)
	public String addMedByName(HttpServletRequest req, final ModelMap map) {

		QueryResults<AllMedications> all = allmedDAO.find();
		map.addAttribute("allMeds", all.asList().get(0).getMedicationNames());

		return "addMedByName";
	}

	@RequestMapping(value = "/processAddMedByName", method = RequestMethod.POST)
	public String processAddMedByName(HttpServletRequest req, final ModelMap map) {
		HttpSession session = req.getSession();
		String loggedInUsername = (String) session.getAttribute("username");
		UserProfile user = userDAO.findByUserId(loggedInUsername);

		String medParam = req.getParameter("meds");
		List<String> meds = Arrays.asList(medParam);

		for (String medication : meds) {
			userDAO.addUserMedication(user, new UserMedication(medication));
		}

		return "redirect:/";
	}

	@RequestMapping(value = "/addMedByPharmClass", method = RequestMethod.GET)
	public String addMedByPharmClass(HttpServletRequest req, final ModelMap map) {

		QueryResults<AllPharmClasses> all = allpharmDAO.find();
		map.addAttribute("allPharmClasses", all.asList().get(0).getPharmClassNames());

		return "addMedByPharmClass";
	}

	@RequestMapping(value = "/processAddMedByPharmClass", method = RequestMethod.POST)
	public String processAddMedByPharmClass(HttpServletRequest req, final ModelMap map) throws UnirestException {
		// HttpSession session = req.getSession();
		// String loggedInUsername = (String) session.getAttribute("username");
		// UserProfile user = userDAO.findByUserId(loggedInUsername);

		// String pharmClassesParam = req.getParameter("pharmclasses");
		// List<String> pharmClasses = Arrays.asList(pharmClassesParam);

		// List<String> medications = ApiQueries.findByPharmClass(pharmClasses.get(0)));
		// map.addAttribute("allMeds", medications);

		return "addMedByName";
	}

	private boolean register(String username, String pwd) {
		userDAO.save(new UserProfile(username, pwd));
		UserProfile user = userDAO.findByUserId(username);
		return user.getUserId().equals(username);
	}

	private boolean validate(String username, String pwd) {
		UserProfile user = userDAO.findByUserId(username);
		return user == null ? false : user.getPassword().equals(pwd);
	}

	@PreDestroy
	public void cleanUp() throws Exception {
		logger.info("Disconnecting MongoDB");
		MongoDB.instance().close();
	}
}
