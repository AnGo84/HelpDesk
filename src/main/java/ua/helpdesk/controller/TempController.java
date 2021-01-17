package ua.helpdesk.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempController {
	@RequestMapping("/")
	public String FirstPage() {
		return "We succeed. we are viewing our first page.";
	}

}
