package ua.helpdesk.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ua.helpdesk.entities.Priority;
import ua.helpdesk.service.TableDataService;

/**
 * A converter class used in views to map id's to actual userProfile objects.
 */
//@Component
public class ObjectToPriorityConverter implements Converter<Object, Priority>{

	static final Logger logger = LoggerFactory.getLogger(ObjectToPriorityConverter.class);

	@Autowired
	TableDataService<Priority> priorityService;

	/**
	 * Gets Priority by Id
	 * @see Converter#convert(Object)
	 */
	public Priority convert(Object element) {
		Integer id = Integer.parseInt((String)element);
		Priority priority= priorityService.findById(id);
		logger.info("Priority : {}", priority);
		return priority;
	}
	
}