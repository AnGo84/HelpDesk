package ua.helpdesk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.helpdesk.entity.User;
import ua.helpdesk.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
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
    public FieldError buildFieldErrorOnIsExist(User object, Locale locale) {
        FieldErrorData fieldErrorData = FieldErrorData.builder()
                .fieldName("login")
                .fieldLabel("label.field.login")
                .fieldValue(object.getLogin())
                .build();
        FieldError fieldError = constructFieldError("validation.field.non_unique", fieldErrorData, locale);
        return fieldError;
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

    @PostMapping("/change_password")
    public String processChangePassword(HttpServletRequest request, Model model, Principal principal, Locale locale) {
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
