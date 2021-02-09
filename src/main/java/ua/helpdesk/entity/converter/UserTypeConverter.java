package ua.helpdesk.entity.converter;

import ua.helpdesk.entity.UserType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class UserTypeConverter implements AttributeConverter<UserType, Long> {
    @Override
    public Long convertToDatabaseColumn(UserType userType) {
        if (userType == null) {
            return null;
        }
        return userType.getDbIndex();
    }

    @Override
    public UserType convertToEntityAttribute(Long aLong) {
        return Stream.of(UserType.values()).filter(el -> el.getDbIndex().equals(aLong)).findFirst().orElse(null);
    }
}
