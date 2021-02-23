package ua.helpdesk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import ua.helpdesk.TestDataUtils;
import ua.helpdesk.entity.*;
import ua.helpdesk.exception.ForbiddenOperationException;
import ua.helpdesk.repository.TicketRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TicketServiceImplTest {
	@Autowired
	private TicketServiceImpl ticketService;
	@MockBean
	private TicketRepository mockTicketRepository;

	private Ticket ticket;
	private Ticket newTicket;

	private Category category;
	private AppService service;
	private TicketPriority ticketPriority;
	private User user;
	private User performer;

	@BeforeEach
	public void beforeEach() {

		service = TestDataUtils.getAppService(1l, "ServiceName");
		category = TestDataUtils.getCategory(1l, "CategoryName", service);

		ticketPriority = TestDataUtils.getTicketPriority(1l, "Priority", 24);

		user = TestDataUtils.getUser(1l, "User", "pass1", true, UserType.USER);
		performer = TestDataUtils.getUser(2l, "User Performer", "pass2", true, UserType.SUPPORT);
		// given
		ticket = TestDataUtils.getTicket(1l, "Ticket Number", "Ticket description", "Ticket theme"
				, category, ticketPriority, TicketState.NEW, TicketType.INNOVATION, user, performer, "Solution");

		newTicket = TestDataUtils.getTicket(2l, "Ticket Number2", "Ticket description2", "Ticket theme"
				, category, ticketPriority, TicketState.REJECTED, TicketType.IMPROVEMENT, user, performer, "Solution");

	}

	@Test
	void whenFindById_thenReturnUser() {
		when(mockTicketRepository.findById(1L)).thenReturn(Optional.of(ticket));
		long id = 1;
		Ticket foundTicket = ticketService.get(id);

		// then
		assertNotNull(foundTicket);
		assertNotNull(foundTicket.getId());
		assertEquals(foundTicket.getNumber(), ticket.getNumber());
		assertEquals(foundTicket.getTheme(), ticket.getTheme());
		assertEquals(foundTicket.getDescription(), ticket.getDescription());
		assertEquals(foundTicket.getTicketPriority(), ticket.getTicketPriority());
		assertEquals(foundTicket.getTicketType(), ticket.getTicketType());
		assertEquals(foundTicket.getTicketState(), ticket.getTicketState());
		assertEquals(foundTicket.getCategory(), ticket.getCategory());
		assertEquals(foundTicket.getService(), ticket.getService());
		assertEquals(foundTicket.getUser(), ticket.getUser());
		assertEquals(foundTicket.getPerformer(), ticket.getPerformer());
		assertEquals(foundTicket.getSolution(), ticket.getSolution());
	}

	@Test
	void whenFindById_thenReturnNull() {
		when(mockTicketRepository.getOne(anyLong())).thenReturn(null);

		Ticket found = ticketService.get(ticket.getId());
		assertNull(found);
	}

	@Test
	void whenFindByNumber_thenReturnUser() {
		when(mockTicketRepository.findByNumber(ticket.getNumber())).thenReturn(ticket);
		Ticket foundTicket = ticketService.findByNumber(ticket.getNumber());

		assertNotNull(foundTicket);
		assertNotNull(foundTicket.getId());
		assertEquals(foundTicket.getNumber(), ticket.getNumber());
	}

	@Test
	void whenFindByNumber_thenReturnNull() {
		when(mockTicketRepository.findByNumber(ticket.getNumber())).thenReturn(ticket);
		Ticket found = ticketService.findByNumber("wrong number");
		assertNull(found);
	}

	@Test
	void whenSave_thenSuccess() {
		ticketService.save(newTicket);
		verify(mockTicketRepository, times(1)).save(newTicket);
	}

	@Test
	void whenSave_thenNPE() {
		when(mockTicketRepository.save(any(Ticket.class))).thenThrow(NullPointerException.class);
		assertThrows(NullPointerException.class, () -> {
			ticketService.save(ticket);
		});
	}

	@Test
	void whenAddNew_thenSuccess() {

		when(mockTicketRepository.save(any())).thenReturn(ticket);

		newTicket.setNumber(null);
		newTicket.setDate(null);

		Ticket savedTicket = ticketService.addNew(newTicket);

		assertNotNull(savedTicket.getNumber());
		assertNotNull(savedTicket.getDate());

		verify(mockTicketRepository, times(1)).save(newTicket);
	}

	@Test
	void whenAddNew_thenNPE() {
		when(mockTicketRepository.save(any(Ticket.class))).thenThrow(NullPointerException.class);
		assertThrows(NullPointerException.class, () -> {
			ticketService.save(ticket);
		});
	}

	@Test
	void whenUpdate_thenSuccess() {
		ticketService.update(ticket);
		verify(mockTicketRepository, times(1)).save(ticket);
	}

	@Test
	void whenUpdate_thenThrow() {
		when(mockTicketRepository.save(any(Ticket.class))).thenThrow(NullPointerException.class);
		assertThrows(NullPointerException.class, () -> {
			ticketService.update(ticket);
		});
	}

	@Test
	void whenDeleteById_thenThrowForbiddenOperationException() {
		assertThrows(ForbiddenOperationException.class, () -> {
			ticketService.deleteById(ticket.getId());
		});

		Long notExistId = 1000000l;
		assertThrows(ForbiddenOperationException.class, () -> {
			ticketService.deleteById(notExistId);
		});
	}

	@Test
	void whenDeleteAll_thenThrowForbiddenOperationException() {
		assertThrows(ForbiddenOperationException.class, () -> {
			ticketService.deleteAll();
		});
	}

	@Test
	void whenFindAllObjects() {
		when(mockTicketRepository.findAll()).thenReturn(Arrays.asList(ticket));
		List<Ticket> objects = ticketService.getAll();
		assertNotNull(objects);
		assertFalse(objects.isEmpty());
		assertEquals(objects.size(), 1);
	}

	@Test
	void whenFindAllSortedObjects() {
		when(mockTicketRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))).thenReturn(Arrays.asList(ticket));
		List<Ticket> objects = ticketService.getAllSortedByIdDESC();
		assertNotNull(objects);
		assertFalse(objects.isEmpty());
		assertEquals(objects.size(), 1);
	}

	@Test
	void whenIsObjectExist() {

		assertFalse(ticketService.isExist(null));

		when(mockTicketRepository.findByNumber(ticket.getNumber())).thenReturn(ticket);
		assertFalse(ticketService.isExist(ticket));

		Ticket existTicket = newTicket;

		when(mockTicketRepository.findByNumber(ticket.getNumber())).thenReturn(existTicket);
		assertTrue(ticketService.isExist(ticket));

		ticket.setId(null);
		when(mockTicketRepository.findByNumber(ticket.getNumber())).thenReturn(existTicket);
		assertTrue(ticketService.isExist(ticket));

		when(mockTicketRepository.findByNumber(anyString())).thenReturn(null);
		assertFalse(ticketService.isExist(ticket));


	}

	@Test
	void whenCreateDefaultInstance_thenReturnObject() {
		Ticket newTicket = ticketService.createDefaultInstance();
		assertNotNull(newTicket);
		assertNull(newTicket.getId());
		assertNull(newTicket.getTheme());
		assertNull(newTicket.getDescription());
		assertNull(newTicket.getTicketType());
		assertNull(newTicket.getTicketPriority());
		assertNull(newTicket.getService());
		assertNull(newTicket.getCategory());
		assertNull(newTicket.getDate());
		assertNull(newTicket.getUser());
		assertNull(newTicket.getPerformer());

		assertNotNull(newTicket.getTicketState());
		assertEquals(TicketState.NEW, newTicket.getTicketState());
	}

}