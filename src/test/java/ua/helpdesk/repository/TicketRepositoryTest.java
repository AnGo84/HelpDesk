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
import ua.helpdesk.entity.*;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TicketRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private TicketPriorityRepository ticketPriorityRepository;
    @Autowired
    private UserRepository userRepository;

    private Ticket ticket;
    private Ticket ticketNew;

    private Category category;
    private Service service;
    private TicketPriority ticketPriority;
    private User user;
    private User performer;

    @BeforeAll
    public static void beforeAll() {

    }

    @BeforeEach
    public void beforeEach() {
        ticketRepository.deleteAll();

        serviceRepository.deleteAll();
        categoryRepository.deleteAll();
        ticketPriorityRepository.deleteAll();
        userRepository.deleteAll();

        service = testEntityManager.persistAndFlush(TestDataUtils.getService(null, "TicketName"));
        service = testEntityManager.persistAndFlush(service);

        category = TestDataUtils.getCategory(null, "CategoryName", service);
        category = testEntityManager.persistAndFlush(category);

        ticketPriority = TestDataUtils.getTicketPriority(null, "Priority", 24);
        ticketPriority = testEntityManager.persistAndFlush(ticketPriority);

        user = TestDataUtils.getUser(null, "User", "pass1", true, UserType.USER);
        user = testEntityManager.persistAndFlush(user);
        performer = TestDataUtils.getUser(null, "User Performer", "pass2", true, UserType.SUPPORT);
        performer = testEntityManager.persistAndFlush(performer);
        // given
        ticket = TestDataUtils.getTicket(null, "Ticket Number", "Ticket description", "Ticket theme"
                , category, ticketPriority, TicketState.NEW, TicketType.INNOVATION, user, performer, "Solution");

        ticket = testEntityManager.persistAndFlush(ticket);

        ticketNew = TestDataUtils.getTicket(null, "Ticket Number2", "Ticket description2", "Ticket theme"
                , category, ticketPriority, TicketState.REJECTED, TicketType.IMPROVEMENT, user, performer, "Solution");

    }

    @AfterEach
    public void afterEach() {
    }

    @Test
    public void whenFindByID_thenReturnTicket() {
        // when
        Optional<Ticket> foundTicket = ticketRepository.findById(ticket.getId());
        // then
        assertTrue(foundTicket.isPresent());
        assertEquals(ticket, foundTicket.get());
    }

    @Test
    public void whenFindByIDByNull_thenThrowInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            ticketRepository.findById(null);
        });

    }

    @Test
    public void whenFindByID_thenReturnEmpty() {
        // when
        Optional<Ticket> foundTicket = ticketRepository.findById(10l);
        // then
        assertFalse(foundTicket.isPresent());
    }

    @Test
    public void whenFindByNumber_thenReturnService() {
        // when
        Ticket foundTicket = ticketRepository.findByNumber(ticket.getNumber());
        // then
        assertNotNull(foundTicket);
        assertNotNull(foundTicket.getId());
        assertEquals(ticket.getNumber(), foundTicket.getNumber());
    }

    @Test
    public void whenFindByNumber_thenReturnEmpty() {
        assertNull(ticketRepository.findByNumber("wrong number"));
    }

    @Test
    public void whenFindAll_thenReturnListOfTicket() {
        //given
        testEntityManager.persistAndFlush(ticketNew);
        // when
        List<Ticket> ticketPriorities = ticketRepository.findAll();
        // then
        assertNotNull(ticketPriorities);
        assertFalse(ticketPriorities.isEmpty());
        assertEquals(ticketPriorities.size(), 2);

    }

    @Test
    public void it_should_save_Ticket() {
        ticketNew = ticketRepository.save(ticketNew);
        Ticket foundTicket = ticketRepository.findById(ticketNew.getId()).get();

        // then
        assertNotNull(foundTicket);
        assertNotNull(foundTicket.getId());
        assertEquals(ticketNew.getNumber(), foundTicket.getNumber());
    }

    @Test
    public void whenSaveTicketWithWrongParams_thenThrowException() {
       /* Ticket wrongTicket = TestDataUtils.getTicket(null, "Ticket Number3", "Ticket description3", "Ticket theme3"
                , category, ticketPriority, TicketState.REJECTED, TicketType.IMPROVEMENT, user, performer, "Solution3");
        */
        /*ticket.setNumber(null);
        assertThrows(ConstraintViolationException.class, () -> {
            ticketRepository.save(ticket);
        });
        ticket.setNumber("");
        assertThrows(ConstraintViolationException.class, () -> {
            ticketRepository.save(ticket);
        });*/
        {
            Ticket wrongTicket = TestDataUtils.getTicket(null, "Ticket Number3", null, "Ticket theme3"
                    , category, ticketPriority, TicketState.REJECTED, TicketType.IMPROVEMENT, user, performer, "Solution3");
            assertThrows(ConstraintViolationException.class, () -> {
                ticketRepository.save(wrongTicket);
            });
        }
        {
            Ticket wrongTicket = TestDataUtils.getTicket(null, "Ticket Number3", "", "Ticket theme3"
                    , category, ticketPriority, TicketState.REJECTED, TicketType.IMPROVEMENT, user, performer, "Solution3");
            assertThrows(ConstraintViolationException.class, () -> {
                ticketRepository.save(wrongTicket);
            });
        }
        {
            Ticket wrongTicket = TestDataUtils.getTicket(null, "Ticket Number3", "Description 3", null
                    , category, ticketPriority, TicketState.REJECTED, TicketType.IMPROVEMENT, user, performer, "Solution3");
            assertThrows(ConstraintViolationException.class, () -> {
                ticketRepository.save(wrongTicket);
            });
        }

        {
            Ticket wrongTicket = TestDataUtils.getTicket(null, "Ticket Number3", "Description 3", ""
                    , category, ticketPriority, TicketState.REJECTED, TicketType.IMPROVEMENT, user, performer, "Solution3");
            assertThrows(ConstraintViolationException.class, () -> {
                ticketRepository.save(wrongTicket);
            });
        }

        {
            Ticket wrongTicket = TestDataUtils.getTicket(null, "Ticket Number3", "Description 3", ""
                    , category, ticketPriority, TicketState.REJECTED, TicketType.IMPROVEMENT, user, performer, "Solution3");
            wrongTicket.setService(null);
            assertThrows(ConstraintViolationException.class, () -> {
                ticketRepository.save(wrongTicket);
            });
        }

        {
            Ticket wrongTicket = TestDataUtils.getTicket(null, "Ticket Number3", "Description 3", ""
                    , category, ticketPriority, TicketState.REJECTED, TicketType.IMPROVEMENT, user, performer, "Solution3");
            wrongTicket.setCategory(null);
            assertThrows(ConstraintViolationException.class, () -> {
                ticketRepository.save(wrongTicket);
            });
        }
        {
            Ticket wrongTicket = TestDataUtils.getTicket(null, "Ticket Number3", "Description 3", ""
                    , category, ticketPriority, TicketState.REJECTED, TicketType.IMPROVEMENT, user, performer, "Solution3");
            wrongTicket.setTicketPriority(null);
            assertThrows(ConstraintViolationException.class, () -> {
                ticketRepository.save(wrongTicket);
            });
        }
        {
            Ticket wrongTicket = TestDataUtils.getTicket(null, "Ticket Number3", "Description 3", ""
                    , category, ticketPriority, TicketState.REJECTED, TicketType.IMPROVEMENT, user, performer, "Solution3");
            wrongTicket.setTicketType(null);
            assertThrows(ConstraintViolationException.class, () -> {
                ticketRepository.save(wrongTicket);
            });
        }
        {
            Ticket wrongTicket = TestDataUtils.getTicket(null, "Ticket Number3", "Description 3", ""
                    , category, ticketPriority, TicketState.REJECTED, TicketType.IMPROVEMENT, user, performer, "Solution3");
            wrongTicket.setDate(null);
            assertThrows(ConstraintViolationException.class, () -> {
                ticketRepository.save(wrongTicket);
            });
        }
        {
            Ticket wrongTicket = TestDataUtils.getTicket(null, "Ticket Number3", "Description 3", ""
                    , category, ticketPriority, TicketState.REJECTED, TicketType.IMPROVEMENT, user, performer, "Solution3");
            wrongTicket.setUser(null);
            assertThrows(ConstraintViolationException.class, () -> {
                ticketRepository.save(wrongTicket);
            });
        }
        {
            Ticket wrongTicket = TestDataUtils.getTicket(null, "Ticket Number3", "Description 3", ""
                    , category, ticketPriority, TicketState.REJECTED, TicketType.IMPROVEMENT, user, performer, "Solution3");
            String tooLongText = TestDataUtils.getMockStringWithLength(1001);
            wrongTicket.setSolution(tooLongText);
            assertThrows(ConstraintViolationException.class, () -> {
                ticketRepository.save(wrongTicket);
            });
        }

    }

    @Test
    public void whenSaveTicketWithExistName_thenThrowDataIntegrityViolationException() {
        ticketNew.setNumber(ticket.getNumber());
        assertThrows(DataIntegrityViolationException.class, () -> {
            ticketRepository.save(ticketNew);
        });
    }

    @Test
    public void whenDeleteById_thenOk() {
        //given
        testEntityManager.persistAndFlush(ticketNew);
        assertEquals(ticketRepository.findAll().size(), 2);

        Ticket foundTicket = ticketRepository.findByNumber(ticketNew.getNumber());

        // when
        ticketRepository.deleteById(foundTicket.getId());
        // then
        assertEquals(ticketRepository.findAll().size(), 1);
        assertNull(ticketRepository.findByNumber(ticketNew.getNumber()));
    }

    @Test
    public void whenDeleteById_thenThrowEmptyResultDataAccessException() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            ticketRepository.deleteById(10000000l);
        });
    }
}