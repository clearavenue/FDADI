package com.clearavenue.fdadi;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FDADIController {

	public String errMsg = "";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(final ModelMap map) {
		if (StringUtils.isNotEmpty(errMsg)) {
			map.addAttribute("errMsg", errMsg);
		}
		return "index";
	}
}
