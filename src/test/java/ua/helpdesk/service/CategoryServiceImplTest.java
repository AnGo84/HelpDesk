package ua.helpdesk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.helpdesk.TestDataUtils;
import ua.helpdesk.entity.AppService;
import ua.helpdesk.entity.Category;
import ua.helpdesk.exception.EntityException;
import ua.helpdesk.repository.CategoryRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceImplTest {
    @Autowired
    private CategoryServiceImpl service;
    @MockBean
    private CategoryRepository mockCategoryRepository;

    private AppService appService;
    private Category category;

    @BeforeEach
    public void beforeEach() {
        appService = TestDataUtils.getAppService(1L, "service_name");
        category = TestDataUtils.getCategory(1L, "category_name", appService);
    }

    @Test
    void whenFindById_thenReturnObject() {
        when(mockCategoryRepository.findById(1L)).thenReturn(Optional.of(category));
        long id = 1;
        Category found = service.get(id);

        assertNotNull(found);
        assertEquals(found.getId(), category.getId());
    }

    @Test
    void whenFindById_thenReturnNull() {
        when(mockCategoryRepository.findById(1L)).thenReturn(Optional.of(category));
        long id = 221121;
        Category found = service.get(id);
        assertNull(found);
    }

    @Test
    void whenFindByName_thenReturnNull() {
        when(mockCategoryRepository.findByName(category.getName())).thenReturn(category);
        Category found = service.getByName("wrong name");
        assertNull(found);
    }

    @Test
    void whenSaveObject_thenSuccess() {
        when(mockCategoryRepository.save(any())).thenReturn(category);
        Category newCategory = TestDataUtils.getCategory(null, "new_name", appService);
        Category savedCategory = service.save(newCategory);
        assertNotNull(savedCategory);
        verify(mockCategoryRepository, times(1)).save(newCategory);
    }

    @Test
    void whenSaveObject_thenNPE() {
        when(mockCategoryRepository.save(any(Category.class))).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> {
            service.save(category);
        });
    }

    @Test
    void whenUpdateObject_thenSuccess() {
        category.setName("new_name");
        when(mockCategoryRepository.save(any())).thenReturn(category);
        Category updatedCategory = service.update(category);
        assertNotNull(updatedCategory);
        verify(mockCategoryRepository, times(1)).save(category);
    }

    @Test
    void whenUpdateObject_thenThrow() {
        when(mockCategoryRepository.save(any(Category.class))).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> {
            service.update(category);
        });
    }

    @Test
    void whenDeleteById_thenSuccess() {
        category.setId(1L);
        when(mockCategoryRepository.findById(1L)).thenReturn(Optional.of(category));
        service.deleteById(1l);
        verify(mockCategoryRepository, times(1)).deleteById(1l);
    }

    @Test
    void whenDeleteById_thenThrowEntityException() {
        assertThrows(EntityException.class, () -> {
            service.deleteById(1000000l);
        });
    }

    @Test
    void whenDeleteById_thenThrowEmptyResultDataAccessException() {
        when(mockCategoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        doThrow(new EmptyResultDataAccessException(0)).when(mockCategoryRepository).deleteById(anyLong());
        assertThrows(EmptyResultDataAccessException.class, () -> {
            service.deleteById(1000000l);
        });
    }

    @Test
    void findAllObjects() {
        when(mockCategoryRepository.findAll()).thenReturn(Arrays.asList(category));
        List<Category> appServicesList = service.getAll();
        assertNotNull(appServicesList);
        assertFalse(appServicesList.isEmpty());
        assertEquals(appServicesList.size(), 1);
    }

    @Test
    void isObjectExist() {
        assertFalse(service.isExist(null));
        when(mockCategoryRepository.findByName(category.getName())).thenReturn(null);
        assertFalse(service.isExist(category));

        category.setId(null);
        Category findCategory = TestDataUtils.getCategory(1l, category.getName(), appService);
        when(mockCategoryRepository.findByName(anyString())).thenReturn(findCategory);
        assertTrue(service.isExist(category));

        category.setId(1l);
        findCategory.setName("New name");
        when(mockCategoryRepository.findByName(anyString())).thenReturn(findCategory);
        assertFalse(service.isExist(category));
    }
}