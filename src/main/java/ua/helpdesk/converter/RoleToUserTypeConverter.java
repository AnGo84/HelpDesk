package ua.helpdesk.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ua.helpdesk.model.UserType;
import ua.helpdesk.service.UserTypeService;

/**
 * A converter class used in views to map id's to actual userProfile objects.
 */
@Component
public class RoleToUserTypeConverter implements Converter<Object, UserType>{

	static final Logger logger = LoggerFactory.getLogger(RoleToUserTypeConverter.class);
	
	@Autowired
    UserTypeService userTypeService;

	/**
	 * Gets UserType by Id
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	public UserType convert(Object element) {
		Integer id = Integer.parseInt((String)element);
		UserType userType= userTypeService.findById(id);
		logger.info("UserType : {}",userType);
		return userType;
	}
	
}