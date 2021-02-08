package ua.helpdesk.entities.converter;

import ua.helpdesk.entities.TicketState;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class TicketStateConverter implements AttributeConverter<TicketState, Long> {
    @Override
    public Long convertToDatabaseColumn(TicketState ticketState) {
        if (ticketState == null) {
            return null;
        }
        return ticketState.getDbIndex();
    }

    @Override
    public TicketState convertToEntityAttribute(Long aLong) {
        return Stream.of(TicketState.values()).filter(el -> el.getDbIndex().equals(aLong)).findFirst().orElse(null);
    }
}
