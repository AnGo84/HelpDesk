package ua.helpdesk.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.helpdesk.model.Service;
import ua.helpdesk.service.TableDataService;


@Component
public class ObjectToServiceConverter implements Converter<Object, Service> {

    static final Logger logger = LoggerFactory.getLogger(ObjectToServiceConverter.class);

    @Autowired
    TableDataService<Service> serviceService;

    @Override
    public Service convert(Object element) {
        logger.info("Convert: {}", element);

        if (element instanceof Service) {
            return (Service) element;
        } else {
            Integer id = Integer.parseInt((String) element);
            Service service = serviceService.findById(id);
            return service;
        }
    }

}
