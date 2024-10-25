package com.PruebasPro.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerWebCompartido {
	@GetMapping("/")
	public String compartidoIndexTemplate(Model model) {
		return "index-compartido";
	}
}
