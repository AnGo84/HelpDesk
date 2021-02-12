package ua.helpdesk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.helpdesk.entity.User;
import ua.helpdesk.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
@Slf4j
public class UserController extends AbstractController<User, UserServiceImpl> {

	private final ControllerDataType dataType;
	private final UserServiceImpl service;

	//@Value("${user.password.default}")
	private String userPasswordDefault;
	/*@Autowired
	private BCryptPasswordEncoder passwordEncoder;*/

	private MessageSource messageSource;

	public UserController(UserServiceImpl service, MessageSource messageSource) {
		super(ControllerDataType.USER, User.class, service);
		this.service = service;
		this.messageSource = messageSource;
		this.dataType = ControllerDataType.USER;
	}

	@Override
	public String updateRecord(@Valid User object, BindingResult bindingResult, Model model) {
		log.info("Update '{}': {}", User.class, object);
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				log.error(error.getObjectName() +
						" - " + error.getField() +
						" - " + error.getDefaultMessage() +
						" - " + error.getCode());
			}

			return dataType.getRecordPage();
		}
		if (service.isExist(object)) {
			log.error("Object '{}' exist", object.getLogin());
			String fieldName = messageSource.getMessage("label.login", new String[]{}, getSpringWebLocale());
			FieldError fieldError = new FieldError(OBJECT_ATTRIBUTE, "login",
					messageSource.getMessage("validation.field.non_unique", new String[]{fieldName, object.getLogin()}, getSpringWebLocale()));


			bindingResult.addError(fieldError);
			return dataType.getRecordPage();
		}

		User updated = service.update(object);
		log.info("Updated: {}", updated);
		return "redirect:" + dataType.getListPage();
	}

}
