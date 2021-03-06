package ua.helpdesk.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.helpdesk.entity.AbstractEntity;
import ua.helpdesk.service.CommonService;

import javax.validation.Valid;
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

	public ControllerDataType getControllerData() {
		return controllerData;
	}

	@Override
	public String allRecords(Model model) {
		log.info("Get all objects '{}'", objectClass.getName());
		model.addAttribute("objectsList", service.getAll());
		return controllerData.getListPage();
	}

	@Override
	public String viewRecord(Long id, Model model) {
		log.info("View '{}' with ID= {}", objectClass.getName(), id);
		model.addAttribute(ATTRIBUTE_READ_ONLY, true);
		model.addAttribute(OBJECT_ATTRIBUTE, service.get(id));
		return controllerData.getRecordPage();
	}

	@Override
	public String addRecord(Model model) {
		log.info("Add new '{}' record", objectClass.getName());
		model.addAttribute(ATTRIBUTE_READ_ONLY, false);
		model.addAttribute(OBJECT_ATTRIBUTE, createInstance(objectClass));
		return controllerData.getRecordPage();
	}

	@Override
	public String editRecord(Long id, Model model) {
		log.info("Edit '{}' with ID= {}", objectClass.getName(), id);
		model.addAttribute(ATTRIBUTE_READ_ONLY, false);
		model.addAttribute(OBJECT_ATTRIBUTE, service.get(id));
		return controllerData.getRecordPage();
	}

	@Override
	public String deleteRecord(Long id) {
		log.info("Delete {} with ID= {}", objectClass, id);
		service.deleteById(id);
		return "redirect:" + controllerData.getListPageURL();
	}

	@Override
	public String updateRecord(@Valid @ModelAttribute(OBJECT_ATTRIBUTE) E object, BindingResult bindingResult, Model model, Locale locale) {
		log.debug("Update: {}", object);
		if (bindingResult.hasErrors()) {
			log.debug("Errors: %n {}", buildFieldErrorsLog(bindingResult));
			return getControllerData().getRecordPage();
		}

		if (service.isExist(object)) {
			FieldError fieldError = buildFieldErrorOnIsExist(object, locale);
			log.error("Object exist: {}", fieldError);
			bindingResult.addError(fieldError);
			return getControllerData().getRecordPage();
		}

		E updated = service.update(object);
		log.debug("Updated: {}", updated);
		return "redirect:" + getControllerData().getListPageURL();
	}

	public abstract FieldError buildFieldErrorOnIsExist(E object, Locale locale);

	protected String buildFieldErrorsLog(BindingResult bindingResult) {
		StringBuilder errorText = new StringBuilder("");
		List<FieldError> errors = bindingResult.getFieldErrors();
		for (FieldError error : errors) {
			errorText.append(String.format(ERROR_LINE_FORMAT, error.getObjectName(), error.getField(), error.getDefaultMessage(), error.getCode()));
		}
		return errorText.toString();
	}

	protected FieldError constructFieldError(String messageProperty, FieldErrorData fieldErrorData, Locale locale) {
		String fieldLabelName = getMessageSourceMessage(fieldErrorData.getFieldLabel(), locale);
		FieldError fieldError = new FieldError(OBJECT_ATTRIBUTE, fieldErrorData.getFieldName(),
				messageSource.getMessage(messageProperty, new Object[]{fieldLabelName, fieldErrorData.getFieldValue()}, locale));
		return fieldError;
	}

	protected String getMessageSourceMessage(String label, Locale locale) {
		return messageSource.getMessage(label, new Object[]{}, locale);
	}
}
