package com.fishteam.checkers.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class HomeController {
	@RequestMapping(value = "/")
	public String home(Model model) {
		return "index";
	}
}