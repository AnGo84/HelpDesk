package ua.helpdesk.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.helpdesk.TestDataUtils;
import ua.helpdesk.entity.*;
import ua.helpdesk.service.TicketMessageServiceImpl;
import ua.helpdesk.service.TicketServiceImpl;
import ua.helpdesk.service.UserServiceImpl;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerTest {
    public static final String MAPPED_URL = "/tickets";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TicketServiceImpl mockTicketService;
    @MockBean
    private TicketMessageServiceImpl mockTicketMessageService;
    @MockBean
    private UserServiceImpl mockUserService;
    @MockBean
    private MessageSource mockMessageSource;

    private Ticket ticket;
    private Ticket newTicket;
    private Ticket defaultTicket;

    private TicketMessage ticketMessage;

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

        defaultTicket = new Ticket();
        newTicket.setTicketState(TicketState.NEW);

        ticketMessage = TestDataUtils.getTicketMessage(1l, ticket, user, new Date(), "Some text");

        when(mockTicketService.getAllSortedByIdDESC()).thenReturn(Arrays.asList(ticket));
        when(mockTicketService.get(anyLong())).thenReturn(ticket);
        when(mockTicketService.createDefaultInstance()).thenReturn(defaultTicket);

        when(mockTicketMessageService.getAllByTicket(ticket)).thenReturn(Arrays.asList(ticketMessage));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_MANAGER"})
    public void whenGetListAsAuthorized_thenOk() throws Exception {
        mockMvc.perform(get(MAPPED_URL))
                //.andDo
                .andExpect(status().isOk())
                .andExpect(model().attribute("objectsList", notNullValue()))
                .andExpect(model().attribute("newTicket", notNullValue()))
                .andExpect(view().name("tickets_list_page"));

        mockMvc.perform(get(MAPPED_URL + "/"))
                //.andDo
                .andExpect(status().isOk())
                .andExpect(model().attribute("objectsList", notNullValue()))
                .andExpect(model().attribute("newTicket", notNullValue()))
                .andExpect(view().name("tickets_list_page"));

        mockMvc.perform(get(MAPPED_URL + "/list"))
                //.andDo
                .andExpect(status().isOk())
                .andExpect(model().attribute("objectsList", notNullValue()))
                .andExpect(model().attribute("newTicket", notNullValue()))
                .andExpect(view().name("tickets_list_page"));
    }

    @Test
    public void whenGetListAsNoAuthorized_thenOk() throws Exception {
        mockMvc.perform(get(MAPPED_URL))
                ////.andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenViewRecordAsAuthorized_thenOk() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/view-" + ticket.getId()))
                //.andDo
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ticket"))
                .andExpect(model().attribute("ticket", notNullValue()))
                .andExpect(model().attributeExists("newMessage"))
                .andExpect(model().attributeExists("messagesList"))
                .andExpect(model().attribute("messagesList", notNullValue()))
                .andExpect(view().name("ticket_page"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_USER"})
    public void whenViewRecordAsAuthorizedUSER_thenOk() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/view-" + ticket.getId()))
                //.andDo
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ticket"))
                .andExpect(model().attribute("ticket", notNullValue()))
                .andExpect(model().attributeExists("newMessage"))
                .andExpect(model().attributeExists("messagesList"))
                .andExpect(model().attribute("messagesList", notNullValue()))
                .andExpect(view().name("ticket_page"));
    }

    @Test
    public void whenViewRecordAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/view-" + ticket.getId()))
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenAddNewTicketAsAuthorized_thenOk() throws Exception {
        mockMvc.perform(post(MAPPED_URL + "/add")
                .flashAttr("newTicket", ticket)
        )
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(MAPPED_URL));

        verify(mockTicketService, times(1)).addNew(any());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void whenAddNewTicketAsUser_then403() throws Exception {
        mockMvc.perform(post(MAPPED_URL + "/add")
                .flashAttr("newTicket", ticket)
        )
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(MAPPED_URL));

        verify(mockTicketService, times(1)).addNew(any());
    }

    @Test
    public void whenAddNewTicketAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/add"))
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenUpdateRecordAsAuthorized_thenOk() throws Exception {
        mockMvc.perform(post(MAPPED_URL + "/update")
                .flashAttr("ticket", ticket)
        )
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(MAPPED_URL));

        verify(mockTicketService, times(1)).update(any());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void whenUpdateRecordAsUser_thenOk() throws Exception {
        mockMvc.perform(post(MAPPED_URL + "/update")
                .flashAttr("ticket", ticket)
        )
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(MAPPED_URL));

        verify(mockTicketService, times(1)).update(any());
    }

    @Test
    public void whenUpdateRecordAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/update"))
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    /* messages */

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenAddNewMessageAsAuthorized_thenOk() throws Exception {

        mockMvc.perform(post(MAPPED_URL + "/messages/add")
                .flashAttr("newMessage", ticketMessage)
        )
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/tickets/view-" + ticket.getId()));

        verify(mockUserService, times(1)).findByLogin(any());
        verify(mockTicketMessageService, times(1)).update(any());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void whenAddNewMessageAsUser_then403() throws Exception {
        mockMvc.perform(post(MAPPED_URL + "/messages/add")
                .flashAttr("ticket", ticket)
                .flashAttr("newMessage", ticketMessage)
        )
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/tickets/view-" + ticket.getId()));

        verify(mockUserService, times(1)).findByLogin(any());
        verify(mockTicketMessageService, times(1)).update(any());
    }

    @Test
    public void whenAddNewMessageAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/messages/add"))
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }
}