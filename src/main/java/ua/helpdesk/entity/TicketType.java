package ua.helpdesk.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TicketType {

    ERROR(1l),
    IMPROVEMENT(2l),
    INNOVATION(3l);

    private final Long dbIndex;

}
