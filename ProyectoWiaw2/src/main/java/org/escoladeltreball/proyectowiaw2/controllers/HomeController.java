package org.escoladeltreball.proyectowiaw2.controllers;

import java.security.Principal;
import java.util.Locale;

import org.escoladeltreball.proyectowiaw2.repositories.DirectorDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@Autowired
	DirectorDAO directordao;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, Principal principal) {
		
		try {
			directordao.addDirector();
		} catch (Exception e) {
			logger.debug("director creado");
		}
		
		return "home";
	}
	
}
