package ua.helpdesk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ua.helpdesk.entity.Ticket;
import ua.helpdesk.entity.TicketMessage;
import ua.helpdesk.entity.User;
import ua.helpdesk.service.TicketMessageServiceImpl;
import ua.helpdesk.service.TicketServiceImpl;
import ua.helpdesk.service.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tickets")
@Slf4j
public class TicketController {
	public static final String ERROR_LINE_FORMAT = "%s - %s - %s - %s %n";
	public static final String ATTRIBUTE_READ_ONLY = "readOnly";
	public static final String OBJECT_ATTRIBUTE = "ticket";

	private ControllerDataType controllerData;
	private final TicketServiceImpl ticketService;
	private final TicketMessageServiceImpl ticketMessageService;
	private final UserServiceImpl userService;
	private final MessageSource messageSource;

	public TicketController(TicketServiceImpl ticketService, TicketMessageServiceImpl ticketMessageService, UserServiceImpl userService, MessageSource messageSource) {
		this.ticketService = ticketService;
		this.ticketMessageService = ticketMessageService;
		this.userService = userService;
		this.messageSource = messageSource;

		this.controllerData = ControllerDataType.TICKET;
	}

	@GetMapping(value = {"", "/list", "/all"})
	public String allRecords(Model model) {
		log.info("Get all tickets");
		model.addAttribute("objectsList", ticketService.getAllSortedByIdDESC());
		model.addAttribute("newTicket", ticketService.createDefaultInstance());
		return controllerData.getListPage();
	}

	@GetMapping(value = {"/view-{id}"})
	public String viewRecord(@PathVariable Long id, Model model) {
		log.info("View ticket with ID= {}", id);
		Ticket ticket = ticketService.get(id);
		model.addAttribute(OBJECT_ATTRIBUTE, ticket);

		final TicketMessage ticketMessage = new TicketMessage();
		ticketMessage.setTicket(ticket);
		model.addAttribute("newMessage", ticketMessage);
		model.addAttribute("messagesList", ticketMessageService.getAllByTicket(ticket));
		return controllerData.getRecordPage();
	}

	@PostMapping(value = {"/add"})
	public String addRecord(@ModelAttribute("newTicket") Ticket ticket, Principal principal) {
		log.info("Add new ticket record: {}", ticket);

		User user = userService.findByLogin(principal.getName());
		ticket.setUser(user);
		ticketService.addNew(ticket);
		return "redirect:" + controllerData.getListPageURL();
	}

	@PostMapping(value = "/update")
	public String updateRecord(@Valid @ModelAttribute(OBJECT_ATTRIBUTE) Ticket object, BindingResult bindingResult) {
		log.debug("Update: {}", object);
		if (bindingResult.hasErrors()) {
			log.debug("Errors: %n {}", buildFieldErrorsLog(bindingResult));
			return controllerData.getRecordPage();
		}

		Ticket updated = ticketService.update(object);
		return "redirect:" + controllerData.getListPageURL();
	}

	@PostMapping(value = {"/messages/add"})
	public String addNewMessage(@ModelAttribute("newMessage") TicketMessage ticketMessage, BindingResult bindingResult, Principal principal) {
		log.info("Add new ticketMessage record: {}", ticketMessage);
		if (bindingResult.hasErrors()) {
			log.debug("Errors: %n {}", buildFieldErrorsLog(bindingResult));
			return controllerData.getRecordPage();
		}

		User user = userService.findByLogin(principal.getName());
		ticketMessage.setUser(user);
		ticketMessage.setDate(new Date());
		log.info("Prepared ticketMessage: {}", ticketMessage);
		ticketMessageService.update(ticketMessage);
		return "redirect:/tickets/view-" + ticketMessage.getTicket().getId();
	}

	protected String buildFieldErrorsLog(BindingResult bindingResult) {
		StringBuilder errorText = new StringBuilder();
		List<FieldError> errors = bindingResult.getFieldErrors();
		for (FieldError error : errors) {
			errorText.append(String.format(ERROR_LINE_FORMAT, error.getObjectName(), error.getField(), error.getDefaultMessage(), error.getCode()));
		}
		return errorText.toString();
	}

	/*protected FieldError constructFieldError(String messageProperty, FieldErrorData fieldErrorData, Locale locale) {
		String fieldLabelName = getMessageSourceMessage(fieldErrorData.getFieldLabel(), locale);
		FieldError fieldError = new FieldError(OBJECT_ATTRIBUTE, fieldErrorData.getFieldName(),
				messageSource.getMessage(messageProperty, new Object[]{fieldLabelName, fieldErrorData.getFieldValue()}, locale));
		return fieldError;
	}

	protected String getMessageSourceMessage(String label, Locale locale) {
		return messageSource.getMessage(label, new Object[]{}, locale);
	}*/
}
