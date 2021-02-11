package ua.helpdesk.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import ua.helpdesk.TestDataUtils;
import ua.helpdesk.entity.Category;
import ua.helpdesk.entity.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    private Category category;

    private Service service;

    @BeforeEach
    public void beforeEach() {
        categoryRepository.deleteAll();
        serviceRepository.deleteAll();
        service = testEntityManager.persistAndFlush(TestDataUtils.getService(null, "ServiceName"));

        category = TestDataUtils.getCategory(null, "CategoryName", service);
        category = testEntityManager.persistAndFlush(category);
    }

    @Test
    public void whenFindByFullName_thenReturnObject() {
        Category foundCategory = categoryRepository.findByName(category.getName());

        assertNotNull(foundCategory);
        assertNotNull(foundCategory.getId());
        assertEquals(foundCategory.getName(), category.getName());
        assertNotNull(foundCategory.getService());
        assertEquals(foundCategory.getService(), category.getService());
    }

    @Test
    public void whenFindByFullName_thenReturnEmpty() {
        assertNull(categoryRepository.findByName("wrong name"));
    }


    @Test
    public void whenFindByID_thenReturnCategory() {
        Optional<Category> foundProduction = categoryRepository.findById(category.getId());

        assertTrue(foundProduction.isPresent());
        assertEquals(foundProduction.get(), category);
    }

    @Test
    public void whenFindByIDByNull_thenThrowInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            categoryRepository.findById(null);
        });

    }

    @Test
    public void whenFindByID_thenReturnEmpty() {
        Long wrongId = 123654L;
        Optional<Category> foundProduction = categoryRepository.findById(wrongId);

        assertFalse(foundProduction.isPresent());
    }

    @Test
    public void whenFindAll_thenReturnListOfCategories() {
        Category newCategory = TestDataUtils.getCategory(null, "CategoryName2", service);

        testEntityManager.persistAndFlush(newCategory);

        List<Category> categories = categoryRepository.findAll();

        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertEquals(categories.size(), 2);

    }

    @Test
    public void it_should_save_Category() {
        Category newCategory = TestDataUtils.getCategory(null, "CategoryName2", service);
        newCategory = categoryRepository.save(newCategory);
        Category foundCategory = categoryRepository.findById(newCategory.getId()).get();

        // then
        assertNotNull(foundCategory);
        assertNotNull(foundCategory.getId());
        assertEquals(foundCategory.getName(), newCategory.getName());
        assertNotNull(foundCategory.getService());
        assertEquals(foundCategory.getService(), newCategory.getService());
    }

    @Test
    public void whenSaveCategoryWithNameTooLong_thenThrowDataIntegrityViolationException() {
        Category newCategory = TestDataUtils.getCategory(null, "fullNameWithLengthMoreThen250SymbolsIsTooLongForSavingfullNameWithLengthMoreThen250SymbolsIsTooLongForSavingfullNameWithLengthMoreThen250SymbolsIsTooLongForSavingfullNameWithLengthMoreThen250SymbolsIsTooLongForSavingfullNameWithLengthMoreThen250SymbolsIsTooLongForSaving",
                service);
        assertThrows(ConstraintViolationException.class, () -> {
            categoryRepository.save(newCategory);
        });
    }

    @Test
    public void whenSaveCategoryWithNameTooShortLength_thenThrowConstraintViolationException() {
        Category newCategory = TestDataUtils.getCategory(null, "", service);
        assertThrows(ConstraintViolationException.class, () -> {
            categoryRepository.save(newCategory);
        });
    }

    @Test
    public void whenSaveCategoryWithNameNull_thenThrowConstraintViolationException() {
        Category newCategory = TestDataUtils.getCategory(null, null, service);
        assertThrows(ConstraintViolationException.class, () -> {
            categoryRepository.save(newCategory);
        });
    }

    @Test
    public void whenSaveCategoryWithServiceNull_thenThrowDataIntegrityViolationException() {
        Category newProduction = TestDataUtils.getCategory(null, "CategoryName2", null);
        assertThrows(ConstraintViolationException.class, () -> {
            categoryRepository.save(newProduction);
        });
    }

    @Test
    public void whenSaveCategoryWithNotExistService_thenThrowInvalidDataAccessApiUsageException() {
        {
            Category newCategory = TestDataUtils.getCategory(null, "CategoryName2", new Service());

            assertThrows(InvalidDataAccessApiUsageException.class, () -> {
                categoryRepository.save(newCategory);
            });
        }
        {
            Service notExistService = new Service();
            notExistService.setId(134l);
            notExistService.setName("Not Exist");
            Category newCategory = TestDataUtils.getCategory(null, "CategoryName2", notExistService);

            assertThrows(InvalidDataAccessApiUsageException.class, () -> {
                categoryRepository.save(newCategory);
            });
        }
    }


    @Test
    public void whenDeleteById_thenOk() {
        Category newCategory = TestDataUtils.getCategory(null, "CategoryName2", service);
        newCategory = testEntityManager.persistAndFlush(newCategory);
        assertEquals(categoryRepository.findAll().size(), 2);

        categoryRepository.deleteById(newCategory.getId());

        assertEquals(categoryRepository.findAll().size(), 1);
    }

    @Test
    public void whenDeleteById_thenThrowEmptyResultDataAccessException() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            categoryRepository.deleteById(10000000l);
        });
    }
}