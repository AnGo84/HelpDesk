package ua.helpdesk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.helpdesk.entity.User;
import ua.helpdesk.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/users")
@Slf4j
public class UserController extends AbstractController<User, UserServiceImpl> {

	private static final String ATTRIBUTE_FOR_USER = "ForUser";
	private final UserServiceImpl service;

	//@Value("${user.password.default}")
	private String userPasswordDefault;
	/*@Autowired
	private BCryptPasswordEncoder passwordEncoder;*/

	public UserController(UserServiceImpl service, MessageSource messageSource) {
		super(ControllerDataType.USER, User.class, service, messageSource);
		this.service = service;
	}

	//TODO refresh pass https://www.baeldung.com/updating-your-password/

	@Override
	public String updateRecord(@Valid User object, BindingResult bindingResult, Model model, Locale locale) {
		log.info("Update '{}': {}", User.class, object);
		if (bindingResult.hasErrors()) {
			log.debug("Errors: %n {}", getFieldErrors(bindingResult));
			return getControllerData().getRecordPage();
		}
		if (service.isExist(object)) {
			FieldError fieldError = constructFieldError("validation.field.non_unique", "label.login", object.getLogin(), locale);
			log.error("Object exist: {}", fieldError);
			bindingResult.addError(fieldError);
			return getControllerData().getRecordPage();
		}

		User updated = service.update(object);
		log.debug("Updated: {}", updated);
		return "redirect:" + getControllerData().getListPage();
	}


	@GetMapping(value = {"/viewCurrent"})
	public String viewCurrentUser(Model model) {
		//TODO refactor with getting from view param
		// https://www.baeldung.com/get-user-in-spring-security
		User user = service.findByLogin(
				SecurityContextHolder.getContext().getAuthentication().getName());
		log.debug("View current user: '{}'", user);
		model.addAttribute(ATTRIBUTE_READ_ONLY, false);
		model.addAttribute(ATTRIBUTE_FOR_USER, true);
		model.addAttribute(OBJECT_ATTRIBUTE, user);
		return getControllerData().getRecordPage();
	}

}
