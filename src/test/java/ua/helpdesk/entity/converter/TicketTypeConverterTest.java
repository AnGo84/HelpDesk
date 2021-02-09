package ua.helpdesk.entity.converter;

import org.junit.jupiter.api.Test;
import ua.helpdesk.entity.TicketType;

import static org.junit.jupiter.api.Assertions.*;

class TicketTypeConverterTest {
    private TicketTypeConverter converter = new TicketTypeConverter();

    @Test
    public void shouldConvertProperly() {
        //
        assertNull(converter.convertToDatabaseColumn(null));

        assertEquals(1, converter.convertToDatabaseColumn(TicketType.ERROR));
        assertEquals(2, converter.convertToDatabaseColumn(TicketType.IMPROVEMENT));
        assertEquals(3, converter.convertToDatabaseColumn(TicketType.INNOVATION));
        assertEquals(4, converter.convertToDatabaseColumn(TicketType.CONSULTATION));
        assertEquals(5, converter.convertToDatabaseColumn(TicketType.OTHER));

        //
        assertEquals(null, converter.convertToEntityAttribute(null));
        assertEquals(null, converter.convertToEntityAttribute(0l));
        assertEquals(TicketType.ERROR, converter.convertToEntityAttribute(1l));
        assertEquals(TicketType.IMPROVEMENT, converter.convertToEntityAttribute(2l));
        assertEquals(TicketType.INNOVATION, converter.convertToEntityAttribute(3l));
        assertEquals(TicketType.CONSULTATION, converter.convertToEntityAttribute(4l));
        assertEquals(TicketType.OTHER, converter.convertToEntityAttribute(5l));

        //
        Long lVal = 2l;
        assertEquals(lVal, converter.convertToDatabaseColumn(converter.convertToEntityAttribute(lVal)));

        TicketType dVal = TicketType.IMPROVEMENT;
        assertTrue(dVal.equals(converter.convertToEntityAttribute(converter.convertToDatabaseColumn(dVal))));
    }
}