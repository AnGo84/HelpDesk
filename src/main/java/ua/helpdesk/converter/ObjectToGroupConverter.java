package ua.helpdesk.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ua.helpdesk.entities.Group;
import ua.helpdesk.service.TableDataService;


//@Component
public class ObjectToGroupConverter implements Converter<Object, Group> {

    static final Logger logger = LoggerFactory.getLogger(ObjectToGroupConverter.class);

    @Autowired
    TableDataService<Group> groupService;

    @Override
    public Group convert(Object element) {
        logger.info("Convert group: {}", element);

        if (element instanceof Group) {
            return (Group) element;
        }else if (element instanceof String && (element == null || ((String) element).isEmpty())) {
                return null;
        } else {
            Integer id = Integer.parseInt((String) element);
            Group group = groupService.findById(id);
            return group;
        }
    }

}
