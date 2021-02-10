package ua.helpdesk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.helpdesk.TestDataUtils;
import ua.helpdesk.entity.User;
import ua.helpdesk.entity.UserType;
import ua.helpdesk.exception.EntityException;
import ua.helpdesk.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;
    @MockBean
    private UserRepository mockUserRepository;
    private User user;

    @BeforeEach
    public void beforeEach() {
        user = TestDataUtils.getUser(1l, "Login", "pass", true, UserType.USER);
    }

    @Test
    void whenFindById_thenReturnUser() {
        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));
        long id = 1;
        User foundUser = userService.get(id);

        // then
        assertNotNull(foundUser);
        assertNotNull(foundUser.getId());
        assertEquals(foundUser.getLogin(), user.getLogin());
        assertEquals(foundUser.getUserType(), user.getUserType());
        assertEquals(foundUser.getFirstName(), user.getFirstName());
        assertEquals(foundUser.getLastName(), user.getLastName());
        assertEquals(foundUser.getMiddleName(), user.getMiddleName());
        assertEquals(foundUser.getActive(), user.getActive());
        assertEquals(foundUser.getEmail(), user.getEmail());
        assertEquals(foundUser.getPhone(), user.getPhone());
    }

    @Test
    void whenFindById_thenReturnNull() {
        when(mockUserRepository.getOne(1L)).thenReturn(user);
        long id = 2;
        User found = userService.get(id);
        assertNull(found);
    }

    @Test
    void whenFindByLogin_thenReturnUser() {
        when(mockUserRepository.findByLogin(user.getLogin())).thenReturn(user);
        User foundUser = userService.findByLogin(user.getLogin());

        assertNotNull(foundUser);
        assertNotNull(foundUser.getId());
        assertEquals(foundUser.getLogin(), user.getLogin());
        assertEquals(foundUser.getUserType(), user.getUserType());
        assertEquals(foundUser.getFirstName(), user.getFirstName());
        assertEquals(foundUser.getLastName(), user.getLastName());
        assertEquals(foundUser.getMiddleName(), user.getMiddleName());
        assertEquals(foundUser.getActive(), user.getActive());
        assertEquals(foundUser.getEmail(), user.getEmail());
        assertEquals(foundUser.getPhone(), user.getPhone());
    }

    @Test
    void whenFindByLogin_thenReturnNull() {
        when(mockUserRepository.findByLogin(user.getLogin())).thenReturn(user);
        User found = userService.findByLogin("wrong name");
        assertNull(found);
    }

    @Test
    void whenSave_thenSuccess() {
        User newUser = TestDataUtils.getUser(null, "LoginNew", "passNew", true, UserType.SUPPORT);
        userService.save(newUser);
        verify(mockUserRepository, times(1)).save(newUser);
    }

    @Test
    void whenSave_thenNPE() {
        when(mockUserRepository.save(any(User.class))).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> {
            userService.save(user);
        });
    }

    @Test
    void whenUpdate_thenSuccess() {
        userService.update(user);
        verify(mockUserRepository, times(1)).save(user);
    }

    @Test
    void whenUpdate_thenThrow() {
        when(mockUserRepository.save(any(User.class))).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> {
            userService.update(user);
        });
    }

    @Test
    void whenDeleteById_thenSuccess() {
        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.deleteById(1l);
        verify(mockUserRepository, times(1)).deleteById(1l);
    }

    @Test
    void whenDeleteById_thenThrowEmptyResultDataAccessException() {
        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));
        doThrow(new EmptyResultDataAccessException(0)).when(mockUserRepository).deleteById(anyLong());
        assertThrows(EntityException.class, () -> {
            userService.deleteById(1000000l);
        });
    }

    @Test
    void whenFindAllObjects() {
        when(mockUserRepository.findAll()).thenReturn(Arrays.asList(user));
        List<User> objects = userService.getAll();
        assertNotNull(objects);
        assertFalse(objects.isEmpty());
        assertEquals(objects.size(), 1);
    }

    @Test
    void whenIsObjectExist() {
        assertFalse(userService.isExist(null));
        User existUser = TestDataUtils.getUser(2l, "LoginNew", "passNew", true, UserType.SUPPORT);

        when(mockUserRepository.findByLogin(user.getLogin())).thenReturn(existUser);
        existUser.setId(null);
        when(mockUserRepository.findByLogin(user.getLogin())).thenReturn(existUser);
        assertTrue(userService.isExist(user));

        when(mockUserRepository.findByLogin(anyString())).thenReturn(null);
        assertFalse(userService.isExist(user));
    }

}