package ua.helpdesk.entity.converter;

import org.junit.jupiter.api.Test;
import ua.helpdesk.entity.TicketState;

import static org.junit.jupiter.api.Assertions.*;

class TicketStateConverterTest {

    private TicketStateConverter converter = new TicketStateConverter();

    @Test
    public void shouldConvertProperly() {
        //
        assertNull(converter.convertToDatabaseColumn(null));

        assertEquals(1, converter.convertToDatabaseColumn(TicketState.NEW));
        assertEquals(2, converter.convertToDatabaseColumn(TicketState.OPEN));
        assertEquals(3, converter.convertToDatabaseColumn(TicketState.PENDING));
        assertEquals(4, converter.convertToDatabaseColumn(TicketState.SOLVED));
        assertEquals(5, converter.convertToDatabaseColumn(TicketState.REJECTED));
        assertEquals(6, converter.convertToDatabaseColumn(TicketState.CLOSE));
        assertEquals(7, converter.convertToDatabaseColumn(TicketState.PROCESSED));
        assertEquals(8, converter.convertToDatabaseColumn(TicketState.TESTING));

        //
        assertEquals(null, converter.convertToEntityAttribute(null));
        assertEquals(null, converter.convertToEntityAttribute(0l));
        assertEquals(TicketState.NEW, converter.convertToEntityAttribute(1l));
        assertEquals(TicketState.PENDING, converter.convertToEntityAttribute(3l));
        assertEquals(TicketState.SOLVED, converter.convertToEntityAttribute(4l));
        assertEquals(TicketState.REJECTED, converter.convertToEntityAttribute(5l));
        assertEquals(TicketState.CLOSE, converter.convertToEntityAttribute(6l));
        assertEquals(TicketState.PROCESSED, converter.convertToEntityAttribute(7l));
        assertEquals(TicketState.TESTING, converter.convertToEntityAttribute(8l));

        //
        Long lVal = 2l;
        assertEquals(lVal, converter.convertToDatabaseColumn(converter.convertToEntityAttribute(lVal)));

        TicketState dVal = TicketState.SOLVED;
        assertTrue(dVal.equals(converter.convertToEntityAttribute(converter.convertToDatabaseColumn(dVal))));
    }
}