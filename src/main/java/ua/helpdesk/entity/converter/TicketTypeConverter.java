package ua.helpdesk.entity.converter;

import ua.helpdesk.entity.TicketType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class TicketTypeConverter implements AttributeConverter<TicketType, Long> {
    @Override
    public Long convertToDatabaseColumn(TicketType ticketType) {
        if (ticketType == null) {
            return null;
        }
        return ticketType.getDbIndex();
    }

    @Override
    public TicketType convertToEntityAttribute(Long aLong) {
        return Stream.of(TicketType.values()).filter(el -> el.getDbIndex().equals(aLong)).findFirst().orElse(null);
    }
}
