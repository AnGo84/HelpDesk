package ua.helpdesk.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ua.helpdesk.TestDataUtils;
import ua.helpdesk.entity.AppService;
import ua.helpdesk.entity.Category;
import ua.helpdesk.service.CategoryServiceImpl;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IndexPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryServiceImpl mockCategoryService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_SUPPORT"})
    public void whenShowMainPageAsSupport_thenRedirect() throws Exception {
        mockMvc.perform(get("/"))
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/tickets"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_USER"})
    public void whenShowMainPageAsUser_thenRedirect() throws Exception {
        mockMvc.perform(get("/"))
                //.andDo
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/tickets"));
    }

    @Test
    public void whenShowMainPageAsNoAuthorized_thenOk() throws Exception {
        mockMvc.perform(get("/"))
                ////.andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_SUPPORT"})
    public void whenLoadCategoriesByServiceAsSupport_thenRedirect() throws Exception {
        long serviceId = 1l;
        AppService appService = TestDataUtils.getAppService(1L, "service_name");
        Category category = TestDataUtils.getCategory(1L, "category_name", appService);

        when(mockCategoryService.getAllByAppService(anyLong())).thenReturn(Arrays.asList(category));

        MvcResult result = mockMvc.perform(get("/loadCategoriesByService/" + serviceId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains(category.getName()));
        assertTrue(content.contains(category.getAppService().getName()));
    }

    @Test
    public void whenLoadCategoriesByServiceAsNoAuthorized_thenOk() throws Exception {
        long serviceId = 1l;
        mockMvc.perform(get("/loadCategoriesByService/" + serviceId))
                ////.andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
    }
}