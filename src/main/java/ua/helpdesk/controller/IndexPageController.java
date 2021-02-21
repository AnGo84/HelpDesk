package ua.helpdesk.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.helpdesk.service.CategoryServiceImpl;

@Controller
@RequestMapping
@Slf4j
public class IndexPageController {

	private final CategoryServiceImpl categoryService;
	private Gson gson;

	public IndexPageController(CategoryServiceImpl categoryService) {
		this.categoryService = categoryService;
		this.gson = new Gson();
	}

	@GetMapping(value = {"/"})
	public String showMainPage(Model model) {
		log.debug("Redirect to tickets page");
		return "redirect:/tickets";
	}

	@ResponseBody
	@GetMapping(value = "loadCategoriesByCountry/{id}")
	public String loadCategoriesByCountry(@PathVariable("id") Long id) {
		log.info("Load Categories by Country id={}", id);
		return gson.toJson(categoryService.findByAppService(id));
	}
}
