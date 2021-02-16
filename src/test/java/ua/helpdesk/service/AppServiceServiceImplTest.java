package ua.helpdesk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.helpdesk.TestDataUtils;
import ua.helpdesk.entity.AppService;
import ua.helpdesk.exception.EntityException;
import ua.helpdesk.repository.AppServiceRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AppServiceServiceImplTest {
	@Autowired
	private AppServiceServiceImpl service;
	@MockBean
	private AppServiceRepository mockAppServiceRepository;
	private AppService appService;

	@BeforeEach
	public void beforeEach() {
		appService = TestDataUtils.getAppService(null, "service_name");
	}

	@Test
	void whenFindById_thenReturnObject() {
		when(mockAppServiceRepository.findById(1L)).thenReturn(Optional.of(appService));
		long id = 1;
		AppService found = service.get(id);

		assertNotNull(found);
		assertEquals(found.getId(), appService.getId());
	}

	@Test
	void whenFindById_thenReturnNull() {
		when(mockAppServiceRepository.findById(1L)).thenReturn(Optional.of(appService));
		long id = 221121;
		AppService found = service.get(id);
		assertNull(found);
	}

	@Test
	void whenFindByName_thenReturnNull() {
		when(mockAppServiceRepository.findByName(appService.getName())).thenReturn(appService);
		AppService found = service.getByName("wrong name");
		assertNull(found);
	}

	@Test
	void whenSaveObject_thenSuccess() {
		when(mockAppServiceRepository.save(any())).thenReturn(appService);
		AppService newAppService = TestDataUtils.getAppService(null, "new_name");
		AppService savedAppService = service.save(newAppService);
		assertNotNull(savedAppService);
		verify(mockAppServiceRepository, times(1)).save(newAppService);
	}

	@Test
	void whenSaveObject_thenNPE() {
		when(mockAppServiceRepository.save(any(AppService.class))).thenThrow(NullPointerException.class);
		assertThrows(NullPointerException.class, () -> {
			service.save(appService);
		});
	}

	@Test
	void whenUpdateObject_thenSuccess() {
		appService.setName("new_name");
		when(mockAppServiceRepository.save(any())).thenReturn(appService);
		AppService updatedAppService = service.update(appService);
		assertNotNull(updatedAppService);
		verify(mockAppServiceRepository, times(1)).save(appService);
	}

	@Test
	void whenUpdateObject_thenThrow() {
		when(mockAppServiceRepository.save(any(AppService.class))).thenThrow(NullPointerException.class);
		assertThrows(NullPointerException.class, () -> {
			service.update(appService);
		});
	}

	@Test
	void whenDeleteById_thenSuccess() {
		appService.setId(1L);
		when(mockAppServiceRepository.findById(1L)).thenReturn(Optional.of(appService));
		service.deleteById(1l);
		verify(mockAppServiceRepository, times(1)).deleteById(1l);
	}

	@Test
	void whenDeleteById_thenThrowEntityException() {
		assertThrows(EntityException.class, () -> {
			service.deleteById(1000000l);
		});
	}

	@Test
	void whenDeleteById_thenThrowEmptyResultDataAccessException() {
		when(mockAppServiceRepository.findById(anyLong())).thenReturn(Optional.of(appService));
		doThrow(new EmptyResultDataAccessException(0)).when(mockAppServiceRepository).deleteById(anyLong());
		assertThrows(EmptyResultDataAccessException.class, () -> {
			service.deleteById(1000000l);
		});
	}

	@Test
	void findAllObjects() {
		when(mockAppServiceRepository.findAll()).thenReturn(Arrays.asList(appService));
		List<AppService> appServicesList = service.getAll();
		assertNotNull(appServicesList);
		assertFalse(appServicesList.isEmpty());
		assertEquals(appServicesList.size(), 1);
	}

	@Test
	void isObjectExist() {
		assertFalse(service.isExist(null));
		when(mockAppServiceRepository.findByName(appService.getName())).thenReturn(null);
		assertFalse(service.isExist(appService));
		AppService findAppService = TestDataUtils.getAppService(1l, appService.getName());
		when(mockAppServiceRepository.findByName(anyString())).thenReturn(findAppService);
		assertTrue(service.isExist(appService));

		appService.setId(1l);
		findAppService.setName("New name");
		when(mockAppServiceRepository.findByName(anyString())).thenReturn(findAppService);
		assertFalse(service.isExist(appService));
	}

}