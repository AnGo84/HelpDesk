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
import ua.helpdesk.entity.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ServiceRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ServiceRepository serviceRepository;

    private Service service;

    @BeforeAll
    public static void beforeAll() {

    }

    @BeforeEach
    public void beforeEach() {
        serviceRepository.deleteAll();
        // given
        service = TestDataUtils.getService(null, "name");

        entityManager.persistAndFlush(service);
    }

    @AfterEach
    public void afterEach() {
    }

    @Test
    public void whenFindByServiceName_thenReturnService() {
        // when
        Service foundService = serviceRepository.findByName(service.getName());
        // then
        assertNotNull(foundService);
        assertNotNull(foundService.getId());
        assertEquals(foundService.getName(), service.getName());
    }

    @Test
    public void whenFindByServiceName_thenReturnEmpty() {
        assertNull(serviceRepository.findByName("wrong name"));
    }

    @Test
    public void whenFindByID_thenReturnService() {
        // when
        Optional<Service> foundService = serviceRepository.findById(service.getId());
        // then
        assertTrue(foundService.isPresent());
        assertEquals(service, foundService.get());
    }

    @Test
    public void whenFindByIDByNull_thenThrowInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            serviceRepository.findById(null);
        });

    }

    @Test
    public void whenFindByID_thenReturnEmpty() {
        // when
        Optional<Service> foundService = serviceRepository.findById(10l);
        // then
        assertFalse(foundService.isPresent());
    }

    @Test
    public void whenFindAll_thenReturnListOfService() {
        //given
        Service Service = TestDataUtils.getService(null, "Service2");
        entityManager.persistAndFlush(Service);
        // when
        List<Service> ticketPriorities = serviceRepository.findAll();
        // then
        assertNotNull(ticketPriorities);
        assertFalse(ticketPriorities.isEmpty());
        assertEquals(ticketPriorities.size(), 2);

    }

    @Test
    public void it_should_save_Service() {
        Service newService = TestDataUtils.getService(null, "Service2");
        serviceRepository.save(newService);
        Service foundService = serviceRepository.findByName(newService.getName());

        // then
        assertNotNull(foundService);
        assertNotNull(foundService.getId());
        assertEquals(foundService.getName(), newService.getName());
    }

    @Test
    public void whenSaveServiceWithNameTooLong_thenThrowConstraintViolationException() {
        Service Service = TestDataUtils.getService(null, "NameWithLengthMoreThen100SymbolsIsTooLongForSavingNameWithLengthMoreThen100SymbolsIsTooLongForSaving!");
        assertThrows(ConstraintViolationException.class, () -> {
            serviceRepository.save(Service);
        });
    }

    @Test
    public void whenSaveServiceWithNameTooShortLength_thenThrowConstraintViolationException() {
        {
            Service Service = TestDataUtils.getService(null, null);
            assertThrows(ConstraintViolationException.class, () -> {
                serviceRepository.save(Service);
            });
        }
        {
            Service Service = TestDataUtils.getService(null, "");
            assertThrows(ConstraintViolationException.class, () -> {
                serviceRepository.save(Service);
            });
        }
    }

    @Test
    public void whenSaveServiceWithExistName_thenThrowDataIntegrityViolationException() {
        Service Service = TestDataUtils.getService(null, "name");
        assertThrows(DataIntegrityViolationException.class, () -> {
            serviceRepository.save(Service);
        });
    }

    @Test
    public void whenDeleteById_thenOk() {
        //given
        Service Service = TestDataUtils.getService(null, "Service2");
        entityManager.persistAndFlush(Service);
        assertEquals(serviceRepository.findAll().size(), 2);

        Service foundService = serviceRepository.findByName("Service2");

        // when
        serviceRepository.deleteById(foundService.getId());
        // then
        assertEquals(serviceRepository.findAll().size(), 1);
    }

    @Test
    public void whenDeleteById_thenThrowEmptyResultDataAccessException() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            serviceRepository.deleteById(10000000l);
        });
    }
}