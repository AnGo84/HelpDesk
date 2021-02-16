package ua.helpdesk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.helpdesk.entity.Category;
import ua.helpdesk.service.CategoryServiceImpl;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/categories")
@Slf4j
public class CategoryController extends AbstractController<Category, CategoryServiceImpl> {

	private final CategoryServiceImpl service;

	public CategoryController(CategoryServiceImpl service, MessageSource messageSource) {
		super(ControllerDataType.CATEGORY, Category.class, service, messageSource);
		this.service = service;
	}

	@Override
	public String updateRecord(@Valid @ModelAttribute("object") Category object, BindingResult bindingResult, Model model, Locale locale) {
		log.debug("Update: {}", object);
		if (bindingResult.hasErrors()) {
			log.debug("Errors: %n {}", buildFieldErrorsLog(bindingResult));
			return getControllerData().getRecordPage();
		}

		if (service.isExist(object)) {
			FieldErrorData fieldErrorData = FieldErrorData.builder()
					.fieldName("name")
					.fieldLabel("label.field.name")
					.fieldValue(object.getName())
					.build();
			FieldError fieldError = constructFieldError("validation.field.non_unique", fieldErrorData, locale);
			log.error("Object exist: {}", fieldError);
			bindingResult.addError(fieldError);
			return getControllerData().getRecordPage();

		}

		Category updated = service.update(object);
		log.debug("Updated: {}", updated);
		return "redirect:" + getControllerData().getListPageURL();
	}

}
