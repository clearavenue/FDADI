package com.clearavenue.fdadi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.clearavenue.data.DBUtils;
import com.clearavenue.data.objects.UserProfile;

@Controller
public class FDADIController {

	private static final Logger logger = LoggerFactory.getLogger(FDADIController.class);

	public String errMsg = "";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest req, final ModelMap map) {
		HttpSession session = req.getSession();
		String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:/login";
		}

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
			view = "index";
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
			view = "index";
		} else {
			logger.info("register failed");
			errMsg = "Failed to register";
			view = "redirect:/login?loginError";
		}

		return view;
	}

	private boolean register(String username, String pwd) {
		return DBUtils.addUserProfile(username, pwd);
	}

	private boolean validate(String username, String pwd) {
		UserProfile user = DBUtils.findUserProfile(username);
		return user == null ? false : user.getPassword().equals(pwd);
	}
}
