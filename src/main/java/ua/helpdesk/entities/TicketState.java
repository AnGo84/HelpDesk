package ua.helpdesk.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TicketState {

    NEW(1l),
    OPEN(2l),
    PENDING(3l),
    SOLVED(4l),
    REJECTED(5l),
    CLose(6l);

    private final Long dbIndex;
}
