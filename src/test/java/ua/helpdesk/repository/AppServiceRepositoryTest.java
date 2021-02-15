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
import ua.helpdesk.entity.AppService;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppServiceRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AppServiceRepository appServiceRepository;

    private AppService appService;

    @BeforeAll
    public static void beforeAll() {

    }

    @BeforeEach
    public void beforeEach() {
        appServiceRepository.deleteAll();
        // given
        appService = TestDataUtils.getAppService(null, "name");

        appService = testEntityManager.persistAndFlush(appService);
    }

    @AfterEach
    public void afterEach() {
    }

    @Test
    public void whenFindByServiceName_thenReturnService() {
        // when
        AppService foundService = appServiceRepository.findByName(appService.getName());
        // then
        assertNotNull(foundService);
        assertNotNull(foundService.getId());
        assertEquals(foundService.getName(), appService.getName());
    }

    @Test
    public void whenFindByServiceName_thenReturnEmpty() {
        assertNull(appServiceRepository.findByName("wrong name"));
    }

    @Test
    public void whenFindByID_thenReturnService() {
        // when
        Optional<AppService> foundService = appServiceRepository.findById(appService.getId());
        // then
        assertTrue(foundService.isPresent());
        assertEquals(appService, foundService.get());
    }

    @Test
    public void whenFindByIDByNull_thenThrowInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            appServiceRepository.findById(null);
        });

    }

    @Test
    public void whenFindByID_thenReturnEmpty() {
        // when
        Optional<AppService> foundService = appServiceRepository.findById(10l);
        // then
        assertFalse(foundService.isPresent());
    }

    @Test
    public void whenFindAll_thenReturnListOfService() {
        //given
        AppService service = TestDataUtils.getAppService(null, "Service2");
        testEntityManager.persistAndFlush(service);
        // when
        List<AppService> ticketPriorities = appServiceRepository.findAll();
        // then
        assertNotNull(ticketPriorities);
        assertFalse(ticketPriorities.isEmpty());
        assertEquals(ticketPriorities.size(), 2);

    }

    @Test
    public void it_should_save_Service() {
        AppService newService = TestDataUtils.getAppService(null, "Service2");
        appServiceRepository.save(newService);
        AppService foundService = appServiceRepository.findByName(newService.getName());

        // then
        assertNotNull(foundService);
        assertNotNull(foundService.getId());
        assertEquals(foundService.getName(), newService.getName());
    }

    @Test
    public void whenSaveServiceWithNameTooLong_thenThrowConstraintViolationException() {
        AppService service = TestDataUtils.getAppService(null, "NameWithLengthMoreThen100SymbolsIsTooLongForSavingNameWithLengthMoreThen100SymbolsIsTooLongForSaving!");
        assertThrows(ConstraintViolationException.class, () -> {
            appServiceRepository.save(service);
        });
    }

    @Test
    public void whenSaveServiceWithNameTooShortLength_thenThrowConstraintViolationException() {
        {
            AppService service = TestDataUtils.getAppService(null, null);
            assertThrows(ConstraintViolationException.class, () -> {
                appServiceRepository.save(service);
            });
        }
        {
            AppService service = TestDataUtils.getAppService(null, "");
            assertThrows(ConstraintViolationException.class, () -> {
                appServiceRepository.save(service);
            });
        }
    }

    @Test
    public void whenSaveServiceWithExistName_thenThrowDataIntegrityViolationException() {
        AppService newService = TestDataUtils.getAppService(null, "name");
        System.out.println("newService: " + appServiceRepository.findByName(newService.getName()));
        assertThrows(DataIntegrityViolationException.class, () -> {
            appServiceRepository.save(newService);
        });
    }

    @Test
    public void whenDeleteById_thenOk() {
        //given
        AppService service = TestDataUtils.getAppService(null, "Service2");
        testEntityManager.persistAndFlush(service);
        assertEquals(appServiceRepository.findAll().size(), 2);

        AppService foundService = appServiceRepository.findByName("Service2");

        // when
        appServiceRepository.deleteById(foundService.getId());
        // then
        assertEquals(appServiceRepository.findAll().size(), 1);
    }

    @Test
    public void whenDeleteById_thenThrowEmptyResultDataAccessException() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            appServiceRepository.deleteById(10000000l);
        });
    }
}