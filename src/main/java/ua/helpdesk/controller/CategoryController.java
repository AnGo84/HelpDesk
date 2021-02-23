package ua.helpdesk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.helpdesk.entity.Category;
import ua.helpdesk.service.CategoryServiceImpl;

import java.util.Locale;

@Controller
@RequestMapping("/categories")
@Slf4j
public class CategoryController extends AbstractController<Category, CategoryServiceImpl> {

	public CategoryController(CategoryServiceImpl service, MessageSource messageSource) {
		super(ControllerDataType.CATEGORY, Category.class, service, messageSource);
	}

	@Override
	public FieldError buildFieldErrorOnIsExist(Category object, Locale locale) {
		FieldErrorData fieldErrorData = FieldErrorData.builder()
				.fieldName("name")
				.fieldLabel("label.field.name")
				.fieldValue(object.getName())
				.build();
		FieldError fieldError = constructFieldError("validation.field.non_unique", fieldErrorData, locale);
		return fieldError;
	}
}
