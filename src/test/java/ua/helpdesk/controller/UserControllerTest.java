package ua.helpdesk.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.helpdesk.TestDataUtils;
import ua.helpdesk.entity.User;
import ua.helpdesk.entity.UserType;
import ua.helpdesk.service.UserServiceImpl;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    public static final String MAPPED_URL = "/users";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl mockUserService;

    private User user;

    @BeforeEach
    public void beforeEach() {
        user = TestDataUtils.getUser(1l, "Login", "pass", true, UserType.USER);

        when(mockUserService.getAll()).thenReturn(Arrays.asList(user));
        when(mockUserService.get(anyLong())).thenReturn(user);
        when(mockUserService.createDefaultInstance()).thenReturn(user);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenGetPersonListAsAuthorized_thenOk() throws Exception {
        mockMvc.perform(get(MAPPED_URL))
                ////.andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("objectsList", notNullValue()))
                .andExpect(view().name("users_list_page"));
        mockMvc.perform(get(MAPPED_URL + "/all"))
                ////.andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("objectsList", notNullValue()))
                .andExpect(view().name("users_list_page"));
        mockMvc.perform(get(MAPPED_URL + "/list"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("objectsList", notNullValue()))
                .andExpect(view().name("users_list_page"));
    }

    @Test
    public void whenGetPersonListAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get(MAPPED_URL))
                ////.andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_MANAGER"})
    public void whenGetPersonListAsAuthorizedWithWrongRoleMANAGER_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get(MAPPED_URL))
                ////.andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenGetShowAddUserPageAsAuthorized_thenException() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/add"))
                ////.andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("object"))
                .andExpect(model().attribute("object", notNullValue()))
                .andExpect(model().attribute("readOnly", false))
                .andExpect(model().attributeDoesNotExist("forUser"))
                .andExpect(view().name("user_page"));
    }

    @Test
    public void whenGetShowAddUserPageAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/add"))
                ////.andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenViewRecordAsAuthorized_thenOk() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/view-" + user.getId()))
                //.andDo
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("object"))
                .andExpect(model().attribute("object", notNullValue()))
                .andExpect(model().attribute("readOnly", true))
                .andExpect(model().attributeDoesNotExist("forUser"))
                .andExpect(view().name("user_page"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_USER"})
    public void whenViewRecordAsAuthorizedUSER_thenOk() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/view-" + user.getId()))
                //.andDo
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenViewRecordAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/view-" + user.getId()))
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenViewCurrentUserAsAuthorized_thenOk() throws Exception {
        when(mockUserService.findByLogin("admin")).thenReturn(user);

        mockMvc.perform(get(MAPPED_URL + "/viewCurrent"))
                //.andDo
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("object"))
                .andExpect(model().attribute("object", notNullValue()))
                .andExpect(model().attribute("readOnly", true))
                .andExpect(model().attribute("forUser", true))

                .andExpect(model().attribute("object", hasProperty("id", hasToString("1"))))
                .andExpect(model().attribute("object", hasProperty("login", hasToString(user.getLogin()))))
                .andExpect(model().attribute("object", hasProperty("lastName", hasToString(user.getLastName()))))
                .andExpect(model().attribute("object", hasProperty("firstName", hasToString(user.getFirstName()))))
                .andExpect(model().attribute("object", hasProperty("active", hasToString("true"))))
                .andExpect(model().attribute("object", hasProperty("userType", hasToString("USER"))))
                .andExpect(view().name("user_page"));

    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void whenViewCurrentUserAsAuthorizedUser_thenOk() throws Exception {
        when(mockUserService.findByLogin("user")).thenReturn(user);

        mockMvc.perform(get(MAPPED_URL + "/viewCurrent"))
                //.andDo
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("object"))
                .andExpect(model().attribute("object", notNullValue()))
                .andExpect(model().attribute("readOnly", true))
                .andExpect(model().attribute("forUser", true))

                .andExpect(model().attribute("object", hasProperty("id", hasToString("1"))))
                .andExpect(model().attribute("object", hasProperty("login", hasToString(user.getLogin()))))
                .andExpect(model().attribute("object", hasProperty("lastName", hasToString(user.getLastName()))))
                .andExpect(model().attribute("object", hasProperty("firstName", hasToString(user.getFirstName()))))
                .andExpect(model().attribute("object", hasProperty("active", hasToString("true"))))
                .andExpect(model().attribute("object", hasProperty("userType", hasToString("USER"))))
                .andExpect(view().name("user_page"));

    }

    @Test
    public void whenViewCurrentUserAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/viewCurrent"))
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenEditUserAsAuthorized_thenOk() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/edit-" + user.getId()))
                ////.andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("object"))
                .andExpect(model().attribute("object", notNullValue()))
                .andExpect(model().attribute("readOnly", false))
                .andExpect(model().attributeDoesNotExist("forUser"))
                .andExpect(view().name("user_page"));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void whenEditRecordAsAuthorizedUser_then403() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/edit-" + user.getId()))
                //.andDo
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenEditUserAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/edit-" + user.getId()))
                ////.andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenUpdateUserAsAuthorizedWithNullUser_thenOk() throws Exception {
        mockMvc.perform(post(MAPPED_URL + "/update"))
                ////.andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("object"))
                .andExpect(model().attribute("object", notNullValue()))
                .andExpect(model().attribute("object", hasProperty("id", nullValue())))
                .andExpect(model().attribute("object", hasProperty("login", blankOrNullString())))
                .andExpect(model().attribute("object", hasProperty("active", nullValue())))
                .andExpect(model().attribute("object", hasProperty("userType", nullValue())))
                .andExpect(model().attributeHasFieldErrors("object", "login", "userType", "active", "lastName", "firstName"))
                .andExpect(view().name("user_page"));

        verify(mockUserService, times(0)).update(any());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenUpdateUserAsAuthorizedWithNotNullUser_thenOk() throws Exception {
        mockMvc.perform(post(MAPPED_URL + "/update")
                .flashAttr("object", user)
        )
                //.andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(MAPPED_URL));

        verify(mockUserService, times(1)).update(user);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenUpdateUserAsAuthorizedErrorOnSave() throws Exception {
        doThrow(DataIntegrityViolationException.class).when(mockUserService).update(any(User.class));

        mockMvc.perform(post(MAPPED_URL + "/update")
                .param("id", String.valueOf(user.getId()))
                .param("login", user.getLogin())
                .param("password", "password")
                .param("active", String.valueOf(user.getActive()))
                .param("userType", "USER")
        )
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("object"))
                .andExpect(model().attributeHasFieldErrors("object", "firstName"))
                .andExpect(view().name("user_page"));

        verify(mockUserService, times(0)).update(user);
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void whenUpdateRecordAsAuthorizedUser_then403() throws Exception {
        mockMvc.perform(post(MAPPED_URL + "/update")
                .flashAttr("object", user)
        )
                //.andDo
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenUpdateUserAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(post(MAPPED_URL + "/update"))
                //.andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenDeleteUserAsAuthorizedWithNotNullUser_thenOk() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/delete-" + user.getId()))
                //.andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(MAPPED_URL));

        verify(mockUserService, times(1)).deleteById(user.getId());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void whenDeleteRecordAsAuthorizedUser_then403() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/delete-" + user.getId()))
                //.andDo
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenDeleteUserAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/delete-" + user.getId()))
                //.andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenResetUserPasswordAsAuthorizedWithNotNullUser_thenOk() throws Exception {
        when(mockUserService.get(anyLong())).thenReturn(user);
        when(mockUserService.resetPassword(any())).thenReturn(user);

        mockMvc.perform(get(MAPPED_URL + "/resetPassword-" + user.getId()))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("object"))
                .andExpect(model().attributeExists("viewMessage"))
                .andExpect(model().attribute("viewMessage", "Password reset successfully"))
                .andExpect(view().name("user_page"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenResetUserPasswordAsAuthorizedWithError_thenReturnErrorMessage() throws Exception {
        when(mockUserService.resetPassword(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(get(MAPPED_URL + "/resetPassword-" + user.getId()))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("object"))
                .andExpect(model().attributeExists("viewError"))
                .andExpect(model().attribute("viewError", "Error while reset password"))
                .andExpect(view().name("user_page"));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void whenResetUserPasswordAsAuthorizedUser_then403() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/resetPassword-" + user.getId()))
                //.andDo
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenResetUserPasswordAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/resetPassword-" + user.getId()))
                //.andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenChangePasswordAsAuthorizedWithNotNullUser_thenOk() throws Exception {
        when(mockUserService.findByLogin(any())).thenReturn(user);

        mockMvc.perform(post(MAPPED_URL + "/change_password")
                .param("password", "new_pass"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("messageTitle"))
                .andExpect(model().attributeExists("messageSuccess"))
                .andExpect(model().attribute("messageSuccess", "Password changed successfully"))
                .andExpect(view().name("message_page"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenChangePasswordAsAuthorizedWithNullUser_thenOk() throws Exception {
        when(mockUserService.findByLogin(any())).thenReturn(null);

        mockMvc.perform(post(MAPPED_URL + "/change_password")
                .param("password", "new_pass"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("messageTitle"))
                .andExpect(model().attributeExists("messageError"))
                .andExpect(model().attribute("messageError", "User not found"))
                .andExpect(view().name("message_page"));
    }

    @Test
    public void whenChangePasswordAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(post(MAPPED_URL + "/change_password"))
                //.andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }
}