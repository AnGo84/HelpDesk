package ua.helpdesk.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ua.helpdesk.entities.User;
import ua.helpdesk.service.UserService;


//@Component
public class ObjectToUserConverter implements Converter<Object, User> {

    static final Logger logger = LoggerFactory.getLogger(ObjectToUserConverter.class);

    @Autowired
    UserService userService;

    @Override
    public User convert(Object element) {
        logger.info("Convert user: {}", element);

        if (element instanceof User) {
            logger.info("instanceof user");
            User user = (User) element;
            logger.info("Get user: {}", element);
            return user;
        } else if (element instanceof String && (element == null || ((String) element).isEmpty())) {
            logger.info("instanceof empty String");
            return null;
        } else if (element instanceof String && ((String) element).startsWith("User{")) {
            logger.info("instanceof not empty String");
            String el = ((String) element);
            int first = el.indexOf(",");
            String sub = el.substring(0, first);
            first = sub.indexOf("=");
            if (first != 0 && first < sub.length()) {
                sub = sub.substring(first + 1);
                Integer id = Integer.parseInt(sub);
                User user = userService.findById(id);
                logger.info("find index: " + sub + ", USER: " + user);
                return user;
            } else {
                return null;
            }
        } else {
            logger.info("instanceof not User and not empty String");
            Integer id = Integer.parseInt((String) element);
            User user = userService.findById(id);
            return user;
        }
    }

}
