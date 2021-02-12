package ua.helpdesk.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ua.helpdesk.entity.AbstractEntity;
import ua.helpdesk.service.CommonService;

import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractController<E extends AbstractEntity, S extends CommonService<E>>
		implements CommonController<E> {
	public static final String ERROR_LINE_FORMAT = "%s - %s - %s - %s %n";
	public static final String ATTRIBUTE_READ_ONLY = "readOnly";
	public static final String OBJECT_ATTRIBUTE = "object";

	private final ControllerDataType controllerData;
	private final Class<E> objectClass;
	private final S service;
	private final MessageSource messageSource;

	@Value("${spring.web.locale:en}")
	private String springLocale;

	public ControllerDataType getControllerData() {
		return controllerData;
	}

	@Override
	public String allRecords(Model model) {
		model.addAttribute("objectsList", service.getAll());
		return controllerData.getListPage();
	}

	@Override
	public String viewRecord(Long id, Model model) {
		log.info("View '{}' with ID= {}", objectClass, id);
		model.addAttribute(ATTRIBUTE_READ_ONLY, true);
		model.addAttribute(OBJECT_ATTRIBUTE, service.get(id));
		return controllerData.getRecordPage();
	}

	@Override
	public String addRecord(Model model) {
		log.info("Add new '{}' record", objectClass);
		model.addAttribute(ATTRIBUTE_READ_ONLY, false);
		model.addAttribute(OBJECT_ATTRIBUTE, createInstance(objectClass));
		return controllerData.getRecordPage();
	}

	@Override
	public String editRecord(Long id, Model model) {
		log.info("Edit '{}' with ID= {}", objectClass, id);
		model.addAttribute(ATTRIBUTE_READ_ONLY, false);
		model.addAttribute(OBJECT_ATTRIBUTE, service.get(id));
		return controllerData.getRecordPage();
	}

	@Override
	public String deleteRecord(Long id) {
		log.info("Delete {} with ID= {}", objectClass, id);
		service.deleteById(id);
		return "redirect:" + controllerData.getListPage();
	}

	protected Locale getSpringWebLocale() {
		return new Locale(springLocale);
	}

	protected String getFieldErrors(BindingResult bindingResult) {
		StringBuilder errorText = new StringBuilder("");
		List<FieldError> errors = bindingResult.getFieldErrors();
		for (FieldError error : errors) {
			errorText.append(String.format(ERROR_LINE_FORMAT, error.getObjectName(), error.getField(), error.getDefaultMessage(), error.getCode()));
		}
		return errorText.toString();
	}

	protected FieldError constructFieldError(String messageProperty, String fieldLabel, String fieldValue, Locale locale) {
		String fieldName = messageSource.getMessage(fieldLabel, new String[]{}, locale);
		FieldError fieldError = new FieldError(OBJECT_ATTRIBUTE, "login",
				messageSource.getMessage(messageProperty, new String[]{fieldName, fieldValue}, locale));
		return fieldError;
	}
}
