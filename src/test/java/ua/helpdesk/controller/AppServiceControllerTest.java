package ua.helpdesk.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.helpdesk.TestDataUtils;
import ua.helpdesk.entity.AppService;
import ua.helpdesk.service.AppServiceServiceImpl;

import java.util.Arrays;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AppServiceControllerTest {
    public static final String MAPPED_URL = "/services";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AppServiceServiceImpl mockAppServiceService;

    private AppService appService;

    @BeforeEach
    public void beforeEach() {
        appService = TestDataUtils.getAppService(1l, "service_name");

        when(mockAppServiceService.getAll()).thenReturn(Arrays.asList(appService));
        when(mockAppServiceService.get(anyLong())).thenReturn(appService);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_MANAGER"})
    public void whenGetListAsAuthorized_thenOk() throws Exception {
        mockMvc.perform(get(MAPPED_URL))
                //.andDo
                .andExpect(status().isOk())
                .andExpect(model().attribute("objectsList", notNullValue()))
                .andExpect(view().name("services_list_page"));

        mockMvc.perform(get(MAPPED_URL + "/"))
                //.andDo
                .andExpect(status().isOk())
                .andExpect(model().attribute("objectsList", notNullValue()))
                .andExpect(view().name("services_list_page"));

        mockMvc.perform(get(MAPPED_URL + "/list"))
                //.andDo
                .andExpect(status().isOk())
                .andExpect(model().attribute("objectsList", notNullValue()))
                .andExpect(view().name("services_list_page"));
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
    public void whenShowAddRecordPageAsAuthorized_thenOk() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/add"))
                //.andDo
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("object"))
                .andExpect(model().attribute("object", notNullValue()))
                .andExpect(model().attribute("readOnly", false))
                .andExpect(view().name("service_page"));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void whenShowAddRecordPageAsUser_then403() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/add"))
                //.andDo
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenGetShowAddRecordPageAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/add"))
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenViewRecordAsAuthorized_thenOk() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/view-" + appService.getId()))
                //.andDo
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("object"))
                .andExpect(model().attribute("object", notNullValue()))
                .andExpect(model().attribute("readOnly", true))
                .andExpect(view().name("service_page"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_USER"})
    public void whenViewRecordAsAuthorizedUSER_thenOk() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/view-" + appService.getId()))
                //.andDo
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("object"))
                .andExpect(model().attribute("object", notNullValue()))
                .andExpect(model().attribute("readOnly", true))
                .andExpect(view().name("service_page"));
    }

    @Test
    public void whenViewRecordAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/view-" + appService.getId()))
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenEditRecordAsAuthorized_thenOk() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/edit-" + appService.getId()))
                //.andDo
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("object"))
                .andExpect(model().attribute("object", notNullValue()))
                .andExpect(model().attribute("readOnly", false))
                .andExpect(view().name("service_page"));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void whenEditRecordAsAuthorizedUser_then403() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/edit-" + appService.getId()))
                //.andDo
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenEditRecordAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/edit-" + appService.getId()))
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenUpdateRecordAsAuthorizedWithNullDirectory_thenOk() throws Exception {
        mockMvc.perform(post(MAPPED_URL + "/update")
                .flashAttr("object", appService)
        )
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(MAPPED_URL));

        verify(mockAppServiceService, times(1)).update(any());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void whenUpdateRecordAsAuthorizedUser_then403() throws Exception {
        mockMvc.perform(post(MAPPED_URL + "/update")
                .flashAttr("object", appService)
        )
                //.andDo
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenUpdateRecordAsAuthorizedWithNotNullDirectory_thenOk() throws Exception {

        mockMvc.perform(post(MAPPED_URL + "/update")
                .param("id", String.valueOf(appService.getId()))
                .param("name", appService.getName()))
                //.andDo
                .andExpect(status().isFound())

                .andExpect(redirectedUrl(MAPPED_URL));
        verify(mockAppServiceService, times(1)).update(appService);
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenUpdateRecordAsAuthorizedWithExistName_thenError() throws Exception {
        when(mockAppServiceService.isExist(any())).thenReturn(true);

        mockMvc.perform(post(MAPPED_URL + "/update")
                .param("id", String.valueOf(appService.getId()))
                .param("name", appService.getName()))
                //.andDo
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("object"))
                .andExpect(model().attributeHasFieldErrors("object", "name"))
                .andExpect(view().name("service_page"));
        verify(mockAppServiceService, times(0)).update(appService);
    }

    @Test
    public void whenUpdateRecordAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(post(MAPPED_URL + "/update"))
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void whenDeleteRecordAsAuthorizedWithNotNullDirectory_thenOk() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/delete-" + appService.getId()))
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(MAPPED_URL));

        verify(mockAppServiceService, times(1)).deleteById(appService.getId());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void whenDeleteRecordAsAuthorizedUser_then403() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/delete-" + appService.getId()))
                //.andDo
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenDeleteRecordAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get(MAPPED_URL + "/delete-" + appService.getId()))
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }
}