package ua.helpdesk.entity.converter;

import org.junit.jupiter.api.Test;
import ua.helpdesk.entity.UserType;

import static org.junit.jupiter.api.Assertions.*;

class UserTypeConverterTest {
    private UserTypeConverter converter = new UserTypeConverter();

    @Test
    public void shouldConvertProperly() {
        //
        assertNull(converter.convertToDatabaseColumn(null));

        assertEquals(1, converter.convertToDatabaseColumn(UserType.ADMIN));
        assertEquals(2, converter.convertToDatabaseColumn(UserType.SUPPORT));
        assertEquals(3, converter.convertToDatabaseColumn(UserType.USER));

        //
        assertEquals(null, converter.convertToEntityAttribute(null));
        assertEquals(null, converter.convertToEntityAttribute(0l));
        assertEquals(UserType.ADMIN, converter.convertToEntityAttribute(1l));
        assertEquals(UserType.SUPPORT, converter.convertToEntityAttribute(2l));
        assertEquals(UserType.USER, converter.convertToEntityAttribute(3l));

        //
        Long lVal = 2l;
        assertEquals(lVal, converter.convertToDatabaseColumn(converter.convertToEntityAttribute(lVal)));

        UserType dVal = UserType.SUPPORT;
        assertTrue(dVal.equals(converter.convertToEntityAttribute(converter.convertToDatabaseColumn(dVal))));
    }
}