package ua.helpdesk.controller.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.helpdesk.entity.*;
import ua.helpdesk.service.AppServiceServiceImpl;
import ua.helpdesk.service.CategoryServiceImpl;
import ua.helpdesk.service.TicketPriorityServiceImpl;
import ua.helpdesk.service.UserServiceImpl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ModelAttributeControllerAdvice {
	@Autowired
	private AppServiceServiceImpl appServiceService;
	@Autowired
	private CategoryServiceImpl categoryService;
	@Autowired
	private TicketPriorityServiceImpl priorityService;
	@Autowired
	private UserServiceImpl userService;

	@ModelAttribute("appServicesList")
	public List<AppService> getAppServiceList() {
		List<AppService> resultList = appServiceService.getAll();
		return resultList.stream().sorted(Comparator.comparing(AppService::getName, String.CASE_INSENSITIVE_ORDER))
				.collect(Collectors.toList());
	}

	@ModelAttribute("categoriesList")
	public List<Category> getCategoriesList() {
		List<Category> resultList = categoryService.getAll();
		return resultList.stream().sorted(Comparator.comparing(Category::getName, String.CASE_INSENSITIVE_ORDER))
				.collect(Collectors.toList());
	}

	@ModelAttribute("prioritiesList")
	public List<TicketPriority> getTicketPrioritiesList() {
		List<TicketPriority> resultList = priorityService.getAll();
		return resultList.stream().sorted(Comparator.comparing(TicketPriority::getName, String.CASE_INSENSITIVE_ORDER))
				.collect(Collectors.toList());
	}

	@ModelAttribute("performersList")
	public List<User> getTicketPerformersList() {
		List<User> resultList = userService.getAllByUserTypes(Arrays.asList(UserType.ADMIN, UserType.SUPPORT));
		return resultList;
	}

}
