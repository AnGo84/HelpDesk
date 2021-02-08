package ua.helpdesk.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    ADMIN(1l),
    SUPPORT(2l),
    USER(3l);

    private final Long dbIndex;

}
