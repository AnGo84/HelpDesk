package ua.helpdesk.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.helpdesk.entities.TicketType;
import ua.helpdesk.service.TableDataService;


@Component
public class ObjectToTicketTypeConverter implements Converter<Object, TicketType> {

    static final Logger logger = LoggerFactory.getLogger(ObjectToTicketTypeConverter.class);

    @Autowired
    TableDataService<TicketType> serviceService;

    @Override
    public TicketType convert(Object element) {
        logger.info("Convert: {}", element);

        if (element instanceof TicketType) {
            return (TicketType) element;
        } else {
            Integer id = Integer.parseInt((String) element);
            TicketType ticketType = serviceService.findById(id);
            return ticketType;
        }
    }

}
