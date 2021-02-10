package ua.helpdesk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.helpdesk.TestDataUtils;
import ua.helpdesk.entity.TicketPriority;
import ua.helpdesk.exception.EntityException;
import ua.helpdesk.repository.TicketPriorityRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TicketPriorityServiceImplTest {
    @Autowired
    private TicketPriorityServiceImpl ticketPriorityService;
    @MockBean
    private TicketPriorityRepository mockTicketPriorityRepository;
    private TicketPriority ticketPriority;

    @BeforeEach
    public void beforeEach() {
        ticketPriority = TestDataUtils.getTicketPriority(1l, "TicketPriority", 24);
    }

    @Test
    void whenFindById_thenReturnUser() {
        when(mockTicketPriorityRepository.findById(1L)).thenReturn(Optional.of(ticketPriority));
        long id = 1;
        TicketPriority foundTicketPriority = ticketPriorityService.get(id);

        // then
        assertNotNull(foundTicketPriority);
        assertNotNull(foundTicketPriority.getId());
        assertEquals(foundTicketPriority.getName(), ticketPriority.getName());
        assertEquals(foundTicketPriority.getTimeLimit(), ticketPriority.getTimeLimit());
    }

    @Test
    void whenFindById_thenReturnNull() {
        when(mockTicketPriorityRepository.getOne(1L)).thenReturn(ticketPriority);
        long id = 2;
        TicketPriority found = ticketPriorityService.get(id);
        assertNull(found);
    }

    @Test
    void whenFindByName_thenReturnUser() {
        when(mockTicketPriorityRepository.findByName(ticketPriority.getName())).thenReturn(ticketPriority);
        TicketPriority foundTicketPriority = ticketPriorityService.findByName(ticketPriority.getName());

        assertNotNull(foundTicketPriority);
        assertNotNull(foundTicketPriority.getId());
        assertEquals(foundTicketPriority.getName(), ticketPriority.getName());
        assertEquals(foundTicketPriority.getTimeLimit(), ticketPriority.getTimeLimit());
    }

    @Test
    void whenFindByName_thenReturnNull() {
        when(mockTicketPriorityRepository.findByName(ticketPriority.getName())).thenReturn(ticketPriority);
        TicketPriority found = ticketPriorityService.findByName("wrong name");
        assertNull(found);
    }

    @Test
    void whenSave_thenSuccess() {
        TicketPriority newTicketPriority = TestDataUtils.getTicketPriority(null, "NewTicketPriority", 12);
        ticketPriorityService.save(newTicketPriority);
        verify(mockTicketPriorityRepository, times(1)).save(newTicketPriority);
    }

    @Test
    void whenSave_thenNPE() {
        when(mockTicketPriorityRepository.save(any(TicketPriority.class))).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> {
            ticketPriorityService.save(ticketPriority);
        });
    }

    @Test
    void whenUpdate_thenSuccess() {
        ticketPriorityService.update(ticketPriority);
        verify(mockTicketPriorityRepository, times(1)).save(ticketPriority);
    }

    @Test
    void whenUpdate_thenThrow() {
        when(mockTicketPriorityRepository.save(any(TicketPriority.class))).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> {
            ticketPriorityService.update(ticketPriority);
        });
    }

    @Test
    void whenDeleteById_thenSuccess() {
        when(mockTicketPriorityRepository.findById(1L)).thenReturn(Optional.of(ticketPriority));
        ticketPriorityService.deleteById(1l);
        verify(mockTicketPriorityRepository, times(1)).deleteById(1l);
    }

    @Test
    void whenDeleteById_thenThrowEmptyResultDataAccessException() {
        when(mockTicketPriorityRepository.findById(1L)).thenReturn(Optional.of(ticketPriority));
        doThrow(new EmptyResultDataAccessException(0)).when(mockTicketPriorityRepository).deleteById(anyLong());
        assertThrows(EntityException.class, () -> {
            ticketPriorityService.deleteById(1000000l);
        });
    }

    @Test
    void whenFindAllObjects() {
        when(mockTicketPriorityRepository.findAll()).thenReturn(Arrays.asList(ticketPriority));
        List<TicketPriority> objects = ticketPriorityService.getAll();
        assertNotNull(objects);
        assertFalse(objects.isEmpty());
        assertEquals(objects.size(), 1);
    }

    @Test
    void whenIsObjectExist() {
        assertFalse(ticketPriorityService.isExist(null));
        TicketPriority existTicketPriority = TestDataUtils.getTicketPriority(2l, "NameExist", 18);

        when(mockTicketPriorityRepository.findByName(ticketPriority.getName())).thenReturn(existTicketPriority);
        existTicketPriority.setId(null);
        when(mockTicketPriorityRepository.findByName(ticketPriority.getName())).thenReturn(existTicketPriority);
        assertTrue(ticketPriorityService.isExist(ticketPriority));

        when(mockTicketPriorityRepository.findByName(anyString())).thenReturn(null);
        assertFalse(ticketPriorityService.isExist(ticketPriority));
    }


}