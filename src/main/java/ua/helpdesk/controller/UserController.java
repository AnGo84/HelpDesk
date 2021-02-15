package ua.helpdesk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ua.helpdesk.entity.User;
import ua.helpdesk.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Locale;

@Controller
@RequestMapping("/users")
@Slf4j
public class UserController extends AbstractController<User, UserServiceImpl> {

    private static final String ATTRIBUTE_FOR_USER = "forUser";
    private final UserServiceImpl service;

    public UserController(UserServiceImpl service, MessageSource messageSource) {
        super(ControllerDataType.USER, User.class, service, messageSource);
        this.service = service;
    }

    @Override
    public String updateRecord(@Valid @ModelAttribute("object") User object, BindingResult bindingResult, Model model, Locale locale) {
        log.debug("Update: {}", object);
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
        return "redirect:" + getControllerData().getListPageURL();
    }

    @Override
    public User createInstance(Class<User> clazz) {
        log.debug("Create new USER");
        return service.createDefaultInstance();
    }

    @GetMapping(value = {"/viewCurrent"})
    public String viewCurrentUser(Model model, Principal principal) {
        log.debug("View current user info");
        User user = service.findByLogin(principal.getName());
        log.debug("View current user: '{}'", user);
        model.addAttribute(ATTRIBUTE_READ_ONLY, true);
        model.addAttribute(ATTRIBUTE_FOR_USER, true);
        model.addAttribute(OBJECT_ATTRIBUTE, user);
        return getControllerData().getRecordPage();
    }

    @GetMapping(value = "/resetPassword-{id}")
    public String resetUserPassword(@PathVariable Long id, Model model, Locale locale) {
        log.debug("Reset Pass for user with ID= {}", id);
        User user = service.get(id);
        try {
            user = service.resetPassword(user);

            model.addAttribute("viewMessage", getMessageSourceMessage("message.action.password_reset.success", locale));

        } catch (Exception e) {
            log.error("Error on user password reset: {}", e.getMessage(), e);
            model.addAttribute("viewError", getMessageSourceMessage("message.action.password_reset.error", locale));
        }
        model.addAttribute(OBJECT_ATTRIBUTE, user);
        return getControllerData().getRecordPage();
    }

	/*
	@GetMapping("/change_password")
	public String showResetPasswordForm(Model model) {
		log.debug("Show change current user pass view");
		return "change_password_page";
	}*/

    @PostMapping("/change_password")
    public String processResetPassword(HttpServletRequest request, Model model, Principal principal, Locale locale) {
        log.debug("Change current user pass");
        String password = request.getParameter("password");

        User user = service.findByLogin(principal.getName());
        log.debug("View current user: '{}'", user);

        model.addAttribute("title", "Result");
        model.addAttribute("messageTitle", getMessageSourceMessage("label.action.password.change", locale));

        if (user == null) {
            model.addAttribute("messageError", getMessageSourceMessage("message.object.user.not_found", locale));
        } else {
            service.updatePassword(user, password);
            model.addAttribute("messageSuccess", getMessageSourceMessage("message.action.password_change.success", locale));
        }
        return "message_page";
    }
}
