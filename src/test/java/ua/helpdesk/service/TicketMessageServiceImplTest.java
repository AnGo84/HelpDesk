package ua.helpdesk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.helpdesk.TestDataUtils;
import ua.helpdesk.entity.*;
import ua.helpdesk.exception.EntityException;
import ua.helpdesk.repository.TicketMessageRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class TicketMessageServiceImplTest {

    @Autowired
    private TicketMessageServiceImpl ticketMessageService;
    @MockBean
    private TicketMessageRepository mockTicketMessageRepository;

    private TicketMessage ticketMessage;

    private Ticket ticket;
    private Ticket newTicket;

    private Category category;
    private AppService service;
    private TicketPriority ticketPriority;
    private User user;
    private User performer;

    @BeforeEach
    public void beforeEach() {
        service = TestDataUtils.getAppService(null, "TicketName");

        category = TestDataUtils.getCategory(null, "CategoryName", service);

        ticketPriority = TestDataUtils.getTicketPriority(null, "Priority", 24);

        user = TestDataUtils.getUser(null, "User", "pass1", true, UserType.USER);
        performer = TestDataUtils.getUser(null, "User Performer", "pass2", true, UserType.SUPPORT);

        // given
        ticket = TestDataUtils.getTicket(1l, "Ticket Number", "Ticket description", "Ticket theme",
                category, ticketPriority, TicketState.NEW, TicketType.INNOVATION, user, performer, "Solution");

        newTicket = TestDataUtils.getTicket(2l, "Ticket Number2", "Ticket description2", "Ticket theme",
                category, ticketPriority, TicketState.REJECTED, TicketType.IMPROVEMENT, user, performer, "Solution");

        ticketMessage = TestDataUtils.getTicketMessage(null, ticket, user, new Date(), "Some text");
    }

    @Test
    void whenFindById_thenReturnObject() {
        ticketMessage.setId(1l);
        when(mockTicketMessageRepository.findById(1L)).thenReturn(Optional.of(ticketMessage));
        long id = 1;
        TicketMessage foundTicketMessage = ticketMessageService.get(id);

        // then
        assertNotNull(foundTicketMessage);
        assertNotNull(foundTicketMessage.getId());
        assertEquals(foundTicketMessage.getText(), ticketMessage.getText());
        assertEquals(foundTicketMessage.getUser(), ticketMessage.getUser());
        assertEquals(foundTicketMessage.getDate(), ticketMessage.getDate());
    }

    @Test
    void whenFindById_thenReturnNull() {
        when(mockTicketMessageRepository.getOne(1L)).thenReturn(ticketMessage);
        long id = 2;
        TicketMessage found = ticketMessageService.get(id);
        assertNull(found);
    }

    @Test
    void whenSave_thenSuccess() {
        TicketMessage newTicketMessage = TestDataUtils.getTicketMessage(null, newTicket, performer, new Date(), "new message");
        ticketMessageService.save(newTicketMessage);
        verify(mockTicketMessageRepository, times(1)).save(newTicketMessage);
    }

    @Test
    void whenSave_thenNPE() {
        when(mockTicketMessageRepository.save(any(TicketMessage.class))).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> {
            ticketMessageService.save(ticketMessage);
        });
    }

    @Test
    void whenUpdate_thenSuccess() {
        ticketMessageService.update(ticketMessage);
        verify(mockTicketMessageRepository, times(1)).save(ticketMessage);
    }

    @Test
    void whenUpdate_thenThrow() {
        when(mockTicketMessageRepository.save(any(TicketMessage.class))).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> {
            ticketMessageService.update(ticketMessage);
        });
    }

    @Test
    void whenDeleteById_thenSuccess() {
        when(mockTicketMessageRepository.findById(1L)).thenReturn(Optional.of(ticketMessage));
        ticketMessageService.deleteById(1l);
        verify(mockTicketMessageRepository, times(1)).deleteById(1l);
    }

    @Test
    void whenDeleteById_thenThrowEmptyResultDataAccessException() {
        when(mockTicketMessageRepository.findById(1L)).thenReturn(Optional.of(ticketMessage));
        doThrow(new EmptyResultDataAccessException(0)).when(mockTicketMessageRepository).deleteById(anyLong());
        assertThrows(EntityException.class, () -> {
            ticketMessageService.deleteById(1000000l);
        });
    }

    @Test
    void whenFindAllObjects() {
        when(mockTicketMessageRepository.findAll()).thenReturn(Arrays.asList(ticketMessage));
        List<TicketMessage> objects = ticketMessageService.getAll();
        assertNotNull(objects);
        assertFalse(objects.isEmpty());
        assertEquals(objects.size(), 1);
    }

    @Test
    void whenFindAllByTicket() {
        when(mockTicketMessageRepository.findByTicketOrderByDateDesc(ticket)).thenReturn(Arrays.asList(ticketMessage));
        List<TicketMessage> objects = ticketMessageService.getAllByTicket(ticket);
        assertNotNull(objects);
        assertFalse(objects.isEmpty());
        assertEquals(objects.size(), 1);
    }

    @Test
    void whenIsObjectExist() {

        assertFalse(ticketMessageService.isExist(null));
        assertFalse(ticketMessageService.isExist(new TicketMessage()));
        ticketMessage.setId(null);
        assertFalse(ticketMessageService.isExist(ticketMessage));

        ticketMessage.setId(1L);
        when(mockTicketMessageRepository.getOne(anyLong())).thenReturn(null);
        assertFalse(ticketMessageService.isExist(ticketMessage));

        when(mockTicketMessageRepository.getOne(anyLong())).thenReturn(ticketMessage);
        assertTrue(ticketMessageService.isExist(ticketMessage));

        TicketMessage newTicketMessage = TestDataUtils.getTicketMessage(2l, ticket, user, new Date(), "Some text");
        when(mockTicketMessageRepository.getOne(anyLong())).thenReturn(ticketMessage);
        assertFalse(ticketMessageService.isExist(newTicketMessage));

    }

}