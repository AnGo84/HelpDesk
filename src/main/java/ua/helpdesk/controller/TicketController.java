package ua.helpdesk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ua.helpdesk.entity.Ticket;
import ua.helpdesk.service.TicketServiceImpl;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/tickets")
@Slf4j
public class TicketController {
	public static final String ERROR_LINE_FORMAT = "%s - %s - %s - %s %n";
	public static final String ATTRIBUTE_READ_ONLY = "readOnly";
	public static final String OBJECT_ATTRIBUTE = "object";

	private ControllerDataType controllerData;
	private final TicketServiceImpl service;
	private final MessageSource messageSource;

	public TicketController(TicketServiceImpl service, MessageSource messageSource) {
		this.service = service;
		this.messageSource = messageSource;
		this.controllerData = ControllerDataType.TICKET;
	}

	@GetMapping(value = {"", "/list", "/all"})
	public String allRecords(Model model) {
		log.info("Get all tickets");
		model.addAttribute("objectsList", service.getAll());
		model.addAttribute("ticket", service.createDefaultInstance());
		return controllerData.getListPage();
	}

	@GetMapping(value = {"/view-{id}"})
	public String viewRecord(@PathVariable Long id, Model model) {
		log.info("View ticket with ID= {}", id);
		model.addAttribute(ATTRIBUTE_READ_ONLY, true);
		model.addAttribute(OBJECT_ATTRIBUTE, service.get(id));
		return controllerData.getRecordPage();
	}

	@GetMapping(value = {"/add"})
	public String addRecord(@ModelAttribute("ticket") Ticket ticket, Model model) {
		log.info("Add new ticket record");
		ticket.setDate(new Date());
		service.save(ticket);
		return "redirect:" + controllerData.getListPageURL();
	}

	@PostMapping(value = "/update")
	public String updateRecord(@Valid @ModelAttribute("object") Ticket object, BindingResult bindingResult, Model model, Locale locale) {
		log.debug("Update: {}", object);
		if (bindingResult.hasErrors()) {
			log.debug("Errors: %n {}", buildFieldErrorsLog(bindingResult));
			return controllerData.getRecordPage();
		}

		/*if (service.isExist(object)) {
			FieldError fieldError = buildFieldErrorOnIsExist(object, locale);
			log.error("Object exist: {}", fieldError);
			bindingResult.addError(fieldError);
			return controllerData.getRecordPage();
		}*/

		Ticket updated = service.update(object);
		log.debug("Updated: {}", updated);
		return "redirect:" + controllerData.getListPageURL();
	}

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
