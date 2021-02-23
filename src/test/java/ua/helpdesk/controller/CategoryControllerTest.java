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
import ua.helpdesk.entity.Category;
import ua.helpdesk.service.CategoryServiceImpl;

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
class CategoryControllerTest {
	public static final String MAPPED_URL = "/categories";

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private CategoryServiceImpl mockCategoryService;

	private AppService appService;
	private Category category;

	@BeforeEach
	public void beforeEach() {
		appService = TestDataUtils.getAppService(1L, "service_name");
		category = TestDataUtils.getCategory(1L, "category_name", appService);

		when(mockCategoryService.getAll()).thenReturn(Arrays.asList(category));
		when(mockCategoryService.get(anyLong())).thenReturn(category);
	}

	@Test
	@WithMockUser(username = "admin", authorities = {"ROLE_SUPPORT"})
	public void whenGetListAsAuthorized_thenOk() throws Exception {
		mockMvc.perform(get(MAPPED_URL))
				//.andDo
				.andExpect(status().isOk())
				.andExpect(model().attribute("objectsList", notNullValue()))
				.andExpect(view().name("categories_list_page"));

		mockMvc.perform(get(MAPPED_URL + "/"))
				//.andDo
				.andExpect(status().isOk())
				.andExpect(model().attribute("objectsList", notNullValue()))
				.andExpect(view().name("categories_list_page"));

		mockMvc.perform(get(MAPPED_URL + "/list"))
				//.andDo
				.andExpect(status().isOk())
				.andExpect(model().attribute("objectsList", notNullValue()))
				.andExpect(view().name("categories_list_page"));
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
				.andExpect(view().name("category_page"));
	}

	@Test
	@WithMockUser(username = "user", authorities = {"ROLE_USER"})
	public void whenShowAddRecordPageAsUser_then403() throws Exception {
		mockMvc.perform(get(MAPPED_URL + "/add"))
				//.andDo
				.andExpect(status().isForbidden());
	}

	@Test
	public void whenShowAddRecordPageAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
		mockMvc.perform(get(MAPPED_URL + "/add"))
				//.andDo
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
	}

	@Test
	@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
	public void whenViewRecordAsAuthorized_thenOk() throws Exception {
		mockMvc.perform(get(MAPPED_URL + "/view-" + category.getId()))
				//.andDo
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("object"))
				.andExpect(model().attribute("object", notNullValue()))
				.andExpect(model().attribute("readOnly", true))
				.andExpect(view().name("category_page"));
	}

	@Test
	@WithMockUser(username = "admin", authorities = {"ROLE_USER"})
	public void whenViewRecordAsAuthorizedUSER_thenOk() throws Exception {
		mockMvc.perform(get(MAPPED_URL + "/view-" + category.getId()))
				//.andDo
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("object"))
				.andExpect(model().attribute("object", notNullValue()))
				.andExpect(model().attribute("readOnly", true))
				.andExpect(view().name("category_page"));
	}

	@Test
	public void whenViewRecordAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
		mockMvc.perform(get(MAPPED_URL + "/view-" + category.getId()))
				//.andDo
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
	}

	@Test
	@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
	public void whenEditRecordAsAuthorized_thenOk() throws Exception {
		mockMvc.perform(get(MAPPED_URL + "/edit-" + category.getId()))
				//.andDo
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("object"))
				.andExpect(model().attribute("object", notNullValue()))
				.andExpect(model().attribute("readOnly", false))
				.andExpect(view().name("category_page"));
	}

	@Test
	@WithMockUser(username = "user", authorities = {"ROLE_USER"})
	public void whenEditRecordAsAuthorizedUser_then403() throws Exception {
		mockMvc.perform(get(MAPPED_URL + "/edit-" + category.getId()))
				//.andDo
				.andExpect(status().isForbidden());
	}

	@Test
	public void whenEditRecordAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
		mockMvc.perform(get(MAPPED_URL + "/edit-" + category.getId()))
				//.andDo
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
	}

	@Test
	@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
	public void whenUpdateRecordAsAuthorizedWithNullDirectory_thenOk() throws Exception {
		mockMvc.perform(post(MAPPED_URL + "/update")
				.flashAttr("object", category)
		)
				//.andDo
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(MAPPED_URL));

		verify(mockCategoryService, times(1)).update(any());
	}

	@Test
	@WithMockUser(username = "user", authorities = {"ROLE_USER"})
	public void whenUpdateRecordAsAuthorizedUser_then403() throws Exception {
		mockMvc.perform(post(MAPPED_URL + "/update")
				.flashAttr("object", category)
		)
				//.andDo
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
	public void whenUpdateRecordAsAuthorizedWithNotNullDirectory_thenOk() throws Exception {

		mockMvc.perform(post(MAPPED_URL + "/update")
				.flashAttr("object", category))
				//.andDo
				.andExpect(status().isFound())

				.andExpect(redirectedUrl(MAPPED_URL));
		verify(mockCategoryService, times(1)).update(category);
	}


	@Test
	@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
	public void whenUpdateRecordAsAuthorizedWithExistName_thenError() throws Exception {
		when(mockCategoryService.isExist(any())).thenReturn(true);

		mockMvc.perform(post(MAPPED_URL + "/update")
				.flashAttr("object", category))
				//.andDo
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("object"))
				.andExpect(model().attributeHasFieldErrors("object", "name"))
				.andExpect(view().name("category_page"));
		verify(mockCategoryService, times(0)).update(category);
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
		mockMvc.perform(get(MAPPED_URL + "/delete-" + category.getId()))
				//.andDo
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(MAPPED_URL));

		verify(mockCategoryService, times(1)).deleteById(category.getId());
	}

	@Test
	@WithMockUser(username = "user", authorities = {"ROLE_USER"})
	public void whenDeleteRecordAsAuthorizedUser_then403() throws Exception {
		mockMvc.perform(get(MAPPED_URL + "/delete-" + category.getId()))
				//.andDo
				.andExpect(status().isForbidden());
	}

	@Test
	public void whenDeleteRecordAsNoAuthorized_thenRedirectToLoginPage() throws Exception {
		mockMvc.perform(get(MAPPED_URL + "/delete-" + category.getId()))
				//.andDo
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(TestControllerUtils.HTTP_LOCALHOST_LOGIN_URL));
	}
}