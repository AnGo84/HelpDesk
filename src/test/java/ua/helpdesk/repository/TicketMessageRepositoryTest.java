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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TicketMessageRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TicketMessageRepository ticketMessageRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private AppServiceRepository appServiceRepository;
	@Autowired
	private TicketPriorityRepository ticketPriorityRepository;
    @Autowired
    private UserRepository userRepository;

    private TicketMessage ticketMessage;

    private Ticket ticket;
    private Ticket ticketNew;

	private Category category;
	private AppService service;
	private TicketPriority ticketPriority;
    private User user;
    private User performer;

    @BeforeAll
    public static void beforeAll() {

    }

    @BeforeEach
    public void beforeEach() {
        ticketRepository.deleteAll();
		categoryRepository.deleteAll();
		appServiceRepository.deleteAll();
		ticketPriorityRepository.deleteAll();

        userRepository.deleteAll();

		service = testEntityManager.persistAndFlush(TestDataUtils.getAppService(null, "TicketName"));
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
        ticket = TestDataUtils.getTicket(null, "Ticket Number", "Ticket description", "Ticket theme",
                category, ticketPriority, TicketState.NEW, TicketType.INNOVATION, user, performer, "Solution");

        ticket = testEntityManager.persistAndFlush(ticket);

        ticketNew = TestDataUtils.getTicket(null, "Ticket Number2", "Ticket description2", "Ticket theme",
                category, ticketPriority, TicketState.REJECTED, TicketType.IMPROVEMENT, user, performer, "Solution");

        ticketMessage = TestDataUtils.getTicketMessage(null, ticket, user, new Date(), "Some text");
        testEntityManager.persistAndFlush(ticketMessage);
    }

    @AfterEach
    public void afterEach() {
    }

    @Test
    public void whenFindByID_thenReturnTicket() {
        // when
        Optional<TicketMessage> foundTicketMessage = ticketMessageRepository.findById(ticketMessage.getId());
        // then
        assertTrue(foundTicketMessage.isPresent());
        assertEquals(ticketMessage, foundTicketMessage.get());
    }

    @Test
    public void whenFindByIDByNull_thenThrowInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            ticketMessageRepository.findById(null);
        });

    }

    @Test
    public void whenFindByID_thenReturnEmpty() {
        // when
        Optional<TicketMessage> foundTicketMessage = ticketMessageRepository.findById(10l);
        // then
        assertFalse(foundTicketMessage.isPresent());
    }

    @Test
    public void whenFindByNumber_thenReturnService() {
        // when
        List<TicketMessage> foundTicketMessageList = ticketMessageRepository.findByTicketOrderByDateDesc(ticket);
        // then
        assertNotNull(foundTicketMessageList);
        assertFalse(foundTicketMessageList.isEmpty());
        assertEquals(1, foundTicketMessageList.size());
        TicketMessage foundTicketMessage = foundTicketMessageList.get(0);

        assertNotNull(foundTicketMessage);
        assertEquals(ticketMessage, foundTicketMessage);
    }

    @Test
    public void whenFindAll_thenReturnListOfTicket() {
        TicketMessage newTicketMessage = TestDataUtils.getTicketMessage(null, ticket, performer, new Date(), "New text");
        //given
        ticketMessageRepository.save(newTicketMessage);
        // when
        List<TicketMessage> ticketPriorities = ticketMessageRepository.findAll();
        // then
        assertNotNull(ticketPriorities);
        assertFalse(ticketPriorities.isEmpty());
        assertEquals(ticketPriorities.size(), 2);

    }

    @Test
    public void whenFindAllByTicket_thenReturnListOfTicket() {
        TicketMessage newTicketMessage = TestDataUtils.getTicketMessage(null, ticket, performer, new Date(), "New text");
        //given
        ticketMessageRepository.save(newTicketMessage);
        // when
        List<TicketMessage> ticketPriorities = ticketMessageRepository.findByTicketOrderByDateDesc(ticket);
        // then
        assertNotNull(ticketPriorities);
        assertFalse(ticketPriorities.isEmpty());
        assertEquals(ticketPriorities.size(), 2);

        ticketNew = testEntityManager.persistAndFlush(ticketNew);
        newTicketMessage = TestDataUtils.getTicketMessage(null, ticketNew, performer, new Date(), "New text2");
        //given
        ticketMessageRepository.save(newTicketMessage);
        // when
        ticketPriorities = ticketMessageRepository.findByTicketOrderByDateDesc(ticketNew);
        // then
        assertNotNull(ticketPriorities);
        assertFalse(ticketPriorities.isEmpty());
        assertEquals(ticketPriorities.size(), 1);
    }

    @Test
    public void it_should_save_Ticket() {
        TicketMessage newTicketMessage = TestDataUtils.getTicketMessage(null, ticket, performer, new Date(), "New text");
        ticketMessageRepository.save(newTicketMessage);
        TicketMessage foundTicketMessage = ticketMessageRepository.findById(newTicketMessage.getId()).get();

        // then
        assertNotNull(foundTicketMessage);
        assertNotNull(foundTicketMessage.getId());
        assertEquals(newTicketMessage.getId(), foundTicketMessage.getId());
    }

    @Test
    public void whenSaveTicketWithWrongParams_thenThrowException() {
        {
            TicketMessage wrongTicketMessage = TestDataUtils.getTicketMessage(null, null, performer, new Date(), "New text");
            assertThrows(ConstraintViolationException.class, () -> {
                ticketMessageRepository.save(wrongTicketMessage);
            });
        }
        {
            TicketMessage wrongTicketMessage = TestDataUtils.getTicketMessage(null, ticket, null, new Date(), "New text");
            assertThrows(ConstraintViolationException.class, () -> {
                ticketMessageRepository.save(wrongTicketMessage);
            });
        }
        {
            TicketMessage wrongTicketMessage = TestDataUtils.getTicketMessage(null, ticket, user, null, "New text");
            assertThrows(ConstraintViolationException.class, () -> {
                ticketMessageRepository.save(wrongTicketMessage);
            });
        }

        {
            TicketMessage wrongTicketMessage = TestDataUtils.getTicketMessage(null, ticket, user, new Date(), null);
            assertThrows(ConstraintViolationException.class, () -> {
                ticketMessageRepository.save(wrongTicketMessage);
            });
        }
        {
            TicketMessage wrongTicketMessage = TestDataUtils.getTicketMessage(null, ticket, user, new Date(), "");
            assertThrows(ConstraintViolationException.class, () -> {
                ticketMessageRepository.save(wrongTicketMessage);
            });
        }

        {
            TicketMessage wrongTicketMessage = TestDataUtils.getTicketMessage(null, ticket, user, new Date(), TestDataUtils.getMockStringWithLength(1000));
            assertThrows(ConstraintViolationException.class, () -> {
                ticketMessageRepository.save(wrongTicketMessage);
            });
        }


    }

    @Test
    public void whenSaveTicketWithExistName_thenThrowDataIntegrityViolationException() {
        TicketMessage newTicketMessage = TestDataUtils.getTicketMessage(null, ticket, performer, new Date(), "New text");
        ticketNew.setNumber(ticket.getNumber());
        assertThrows(DataIntegrityViolationException.class, () -> {
            ticketRepository.save(ticketNew);
        });
    }

    @Test
    public void whenDeleteById_thenOk() {
        //given
        ticketNew = ticketRepository.save(ticketNew);
        TicketMessage newTicketMessage = TestDataUtils.getTicketMessage(null, ticketNew, performer, new Date(), "New text");

        newTicketMessage = ticketMessageRepository.save(newTicketMessage);

        assertEquals(ticketMessageRepository.findAll().size(), 2);

        // when
        ticketMessageRepository.deleteById(newTicketMessage.getId());
        // then
        assertEquals(ticketMessageRepository.findAll().size(), 1);
        assertTrue(ticketMessageRepository.findByTicketOrderByDateDesc(ticketNew).isEmpty());
    }

    @Test
    public void whenDeleteById_thenThrowEmptyResultDataAccessException() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            ticketMessageRepository.deleteById(10000000l);
        });
    }
}