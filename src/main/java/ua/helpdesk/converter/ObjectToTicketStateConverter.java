package ua.helpdesk.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.helpdesk.entities.TicketState;
import ua.helpdesk.service.TableDataService;


@Component
public class ObjectToTicketStateConverter implements Converter<Object, TicketState> {

    static final Logger logger = LoggerFactory.getLogger(ObjectToTicketStateConverter.class);

    @Autowired
    TableDataService<TicketState> serviceService;

    @Override
    public TicketState convert(Object element) {
        logger.info("Convert: {}", element);

        if (element instanceof TicketState) {
            return (TicketState) element;
        } else {
            Integer id = Integer.parseInt((String) element);
            TicketState ticketState = serviceService.findById(id);
            return ticketState;
        }
    }

}
