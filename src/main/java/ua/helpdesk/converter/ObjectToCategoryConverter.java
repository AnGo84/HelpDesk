package ua.helpdesk.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ua.helpdesk.entities.Category;
import ua.helpdesk.service.CategoryService;


//@Component
public class ObjectToCategoryConverter implements Converter<Object, Category> {

    static final Logger logger = LoggerFactory.getLogger(ObjectToCategoryConverter.class);

    @Autowired
    CategoryService categoryService;

    @Override
    public Category convert(Object element) {
        logger.info("Convert category: {}", element);
        //logger.info("GET Convert class: {}", element.getClass());

        if (element instanceof Category) {
            return (Category) element;
        } else if (element instanceof String && (element == null || ((String) element).isEmpty())) {
            return null;
        } else {
            Integer id = Integer.parseInt((String) element);
            Category category = categoryService.findById(id);
            return category;
        }
    }

}
