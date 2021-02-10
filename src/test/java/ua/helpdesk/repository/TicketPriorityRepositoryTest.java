package ua.helpdesk.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import ua.helpdesk.TestDataUtils;
import ua.helpdesk.entity.TicketPriority;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TicketPriorityRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TicketPriorityRepository ticketPriorityRepository;

    private TicketPriority ticketPriority;

    @BeforeAll
    public static void beforeAll() {

    }

    @BeforeEach
    public void beforeEach() {
        ticketPriorityRepository.deleteAll();
        // given
        ticketPriority = TestDataUtils.getTicketPriority(null, "Priority_Name", 1);

        testEntityManager.persistAndFlush(ticketPriority);
    }

    @AfterEach
    public void afterEach() {
    }

    @Test
    public void whenFindByTicketPriorityName_thenReturnTicketPriority() {
        // when
        TicketPriority foundTicketPriority = ticketPriorityRepository.findByName(ticketPriority.getName());
        // then
        assertNotNull(foundTicketPriority);
        assertNotNull(foundTicketPriority.getId());
        assertEquals(foundTicketPriority.getName(), ticketPriority.getName());
        assertEquals(foundTicketPriority.getTimeLimit(), ticketPriority.getTimeLimit());
    }

    @Test
    public void whenFindByTicketPriorityName_thenReturnEmpty() {
        assertNull(ticketPriorityRepository.findByName("wrong name"));
    }

    @Test
    public void whenFindByID_thenReturnTicketPriority() {
        // when
        Optional<TicketPriority> foundTicketPriority = ticketPriorityRepository.findById(ticketPriority.getId());
        // then
        assertTrue(foundTicketPriority.isPresent());
        assertEquals(ticketPriority, foundTicketPriority.get());
    }

    @Test
    public void whenFindByIDByNull_thenThrowInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            ticketPriorityRepository.findById(null);
        });

    }

    @Test
    public void whenFindByID_thenReturnEmpty() {
        // when
        Optional<TicketPriority> foundTicketPriority = ticketPriorityRepository.findById(10l);
        // then
        assertFalse(foundTicketPriority.isPresent());
    }

    @Test
    public void whenFindAll_thenReturnListOfTicketPriority() {
        //given
        TicketPriority ticketPriority = TestDataUtils.getTicketPriority(null, "TicketPriority2", 23);
        testEntityManager.persistAndFlush(ticketPriority);
        // when
        List<TicketPriority> ticketPriorities = ticketPriorityRepository.findAll();
        // then
        assertNotNull(ticketPriorities);
        assertFalse(ticketPriorities.isEmpty());
        assertEquals(ticketPriorities.size(), 2);

    }

    @Test
    public void it_should_save_TicketPriority() {
        TicketPriority newTicketPriority = TestDataUtils.getTicketPriority(null, "TicketPriority2", 12);
        ticketPriorityRepository.save(newTicketPriority);
        TicketPriority foundTicketPriority = ticketPriorityRepository.findByName(newTicketPriority.getName());

        // then
        assertNotNull(foundTicketPriority);
        assertNotNull(foundTicketPriority.getId());
        assertEquals(foundTicketPriority.getName(), newTicketPriority.getName());
        assertEquals(foundTicketPriority.getTimeLimit(), newTicketPriority.getTimeLimit());
    }

    @Test
    public void whenSaveTicketPriorityWithNameTooLong_thenThrowConstraintViolationException() {
        TicketPriority ticketPriority = TestDataUtils.getTicketPriority(null, "NameWithLengthMoreThen100SymbolsIsTooLongForSavingNameWithLengthMoreThen100SymbolsIsTooLongForSaving!", 24);
        assertThrows(ConstraintViolationException.class, () -> {
            ticketPriorityRepository.save(ticketPriority);
        });
    }

    @Test
    public void whenSaveTicketPriorityWithNameTooShortLength_thenThrowConstraintViolationException() {
        {
            TicketPriority ticketPriority = TestDataUtils.getTicketPriority(null, null, 34);
            assertThrows(ConstraintViolationException.class, () -> {
                ticketPriorityRepository.save(ticketPriority);
            });
        }
        {
            TicketPriority ticketPriority = TestDataUtils.getTicketPriority(null, "", 34);
            assertThrows(ConstraintViolationException.class, () -> {
                ticketPriorityRepository.save(ticketPriority);
            });
        }
    }

    @Test
    public void whenSaveTicketPriorityWithEmptyTimeLimit_thenThrowConstraintViolationException() {
        TicketPriority ticketPriority = TestDataUtils.getTicketPriority(null, "New Name", null);
        assertThrows(ConstraintViolationException.class, () -> {
            ticketPriorityRepository.save(ticketPriority);
        });
    }

    @Test
    public void whenSaveTicketPriorityWithExistName_thenThrowDataIntegrityViolationException() {
        TicketPriority TicketPriority = TestDataUtils.getTicketPriority(null, "Priority_Name", 654);
        assertThrows(DataIntegrityViolationException.class, () -> {
            ticketPriorityRepository.save(TicketPriority);
        });
    }

    @Test
    public void whenDeleteById_thenOk() {
        //given
        TicketPriority ticketPriority = TestDataUtils.getTicketPriority(null, "TicketPriority2", 12);
        testEntityManager.persistAndFlush(ticketPriority);
        assertEquals(ticketPriorityRepository.findAll().size(), 2);

        TicketPriority foundTicketPriority = ticketPriorityRepository.findByName("TicketPriority2");

        // when
        ticketPriorityRepository.deleteById(foundTicketPriority.getId());
        // then
        assertEquals(ticketPriorityRepository.findAll().size(), 1);
    }

    @Test
    public void whenDeleteById_thenThrowEmptyResultDataAccessException() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            ticketPriorityRepository.deleteById(10000000l);
        });
    }

}