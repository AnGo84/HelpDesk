package ua.helpdesk.controller.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.helpdesk.entity.AppService;
import ua.helpdesk.service.AppServiceServiceImpl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ModelAttributeControllerAdvice {
	@Autowired
	private AppServiceServiceImpl appServiceService;

	@ModelAttribute("appServicesList")
	public List<AppService> getAppServiceList() {
		List<AppService> resultList = appServiceService.getAll();
		return resultList.stream().sorted(Comparator.comparing(AppService::getName, String.CASE_INSENSITIVE_ORDER))
				.collect(Collectors.toList());
	}

}
