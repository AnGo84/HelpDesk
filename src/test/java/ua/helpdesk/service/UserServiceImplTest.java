package ua.helpdesk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public static final String DEFAULT_PASSWORD = "123";
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

    @Test
    void whenCreateDefaultInstance_thenReturnObject() {
        User newUser = userService.createDefaultInstance();
        assertNotNull(newUser);
        assertNull(newUser.getId());
        assertNull(newUser.getLogin());
        assertNull(newUser.getLastName());
        assertNull(newUser.getFirstName());
        assertNull(newUser.getMiddleName());
        assertNull(newUser.getPhone());
        assertNull(newUser.getEmail());
        assertNotNull(newUser.getPassword());
        assertTrue(passwordEncoder.matches(DEFAULT_PASSWORD, newUser.getPassword()));
        assertEquals(UserType.USER, newUser.getUserType());
        assertTrue(newUser.getActive());
    }

    @Test
    void whenResetPassword_thenReturnUpdatedObject() {
        User userWithNewPass = TestDataUtils.getUser(1l, "Login", passwordEncoder.encode(DEFAULT_PASSWORD), true, UserType.USER);
        when(mockUserRepository.save(user)).thenReturn(userWithNewPass);

        User updatedUser = userService.resetPassword(user);
        assertNotNull(updatedUser);
        assertEquals(user.getId(), updatedUser.getId());
        assertEquals(user.getLogin(), updatedUser.getLogin());
        assertEquals(user.getLastName(), updatedUser.getLastName());
        assertEquals(user.getFirstName(), updatedUser.getFirstName());
        assertEquals(user.getMiddleName(), updatedUser.getMiddleName());
        assertEquals(user.getPhone(), updatedUser.getPhone());
        assertEquals(user.getEmail(), updatedUser.getEmail());
        assertEquals(user.getActive(), updatedUser.getActive());
        assertEquals(user.getUserType(), updatedUser.getUserType());
        assertNotEquals(user.getPassword(), updatedUser.getPassword());
        assertTrue(passwordEncoder.matches(DEFAULT_PASSWORD, updatedUser.getPassword()));
    }

    @Test
    void whenUpdatePassword_thenReturnUpdatedObject() {
        String newPass = "newPAss";
        User userWithNewPass = TestDataUtils.getUser(1l, "Login", passwordEncoder.encode(newPass), true, UserType.USER);
        when(mockUserRepository.save(user)).thenReturn(userWithNewPass);

        User updatedUser = userService.updatePassword(user, newPass);
        assertNotNull(updatedUser);
        assertEquals(user.getId(), updatedUser.getId());
        assertEquals(user.getLogin(), updatedUser.getLogin());
        assertEquals(user.getLastName(), updatedUser.getLastName());
        assertEquals(user.getFirstName(), updatedUser.getFirstName());
        assertEquals(user.getMiddleName(), updatedUser.getMiddleName());
        assertEquals(user.getPhone(), updatedUser.getPhone());
        assertEquals(user.getEmail(), updatedUser.getEmail());
        assertEquals(user.getActive(), updatedUser.getActive());
        assertEquals(user.getUserType(), updatedUser.getUserType());
        assertNotEquals(user.getPassword(), updatedUser.getPassword());
        assertTrue(passwordEncoder.matches(newPass, updatedUser.getPassword()));
    }

}