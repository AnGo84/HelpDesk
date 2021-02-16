package ua.helpdesk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.helpdesk.entity.AppService;
import ua.helpdesk.service.AppServiceServiceImpl;

import java.util.Locale;

@Controller
@RequestMapping("/services")
@Slf4j
public class AppServiceController extends AbstractController<AppService, AppServiceServiceImpl> {

	public AppServiceController(AppServiceServiceImpl service, MessageSource messageSource) {
		super(ControllerDataType.SERVICES, AppService.class, service, messageSource);
	}

	@Override
	public FieldError buildFieldErrorOnIsExist(AppService object, Locale locale) {
		FieldErrorData fieldErrorData = FieldErrorData.builder()
				.fieldName("name")
				.fieldLabel("label.field.name")
				.fieldValue(object.getName())
				.build();
		FieldError fieldError = constructFieldError("validation.field.non_unique", fieldErrorData, locale);
		return fieldError;
	}

}
