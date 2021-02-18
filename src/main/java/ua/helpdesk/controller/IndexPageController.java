package ua.helpdesk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@Slf4j
public class IndexPageController {

	@GetMapping(value = {"/"})
	public String showMainPage(Model model) {
		log.debug("Redirect to tickets page");
		return "redirect:/tickets";
	}
}
