package ua.helpdesk.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import ua.helpdesk.TestDataUtils;
import ua.helpdesk.entity.User;
import ua.helpdesk.entity.UserType;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeAll
    public static void beforeAll() {

    }

    @BeforeEach
    public void beforeEach() {
        userRepository.deleteAll();
        // given
        user = TestDataUtils.getUser(null, "User", "password", true, UserType.USER);

        entityManager.persistAndFlush(user);
    }

    @AfterEach
    public void afterEach() {
    }

    @Test
    public void whenFindByUserName_thenReturnUser() {
        // when
        User foundUser = userRepository.findByLogin(user.getLogin());
        // then
        assertNotNull(foundUser);
        assertNotNull(foundUser.getId());
        assertEquals(foundUser.getLogin(), user.getLogin());
        assertEquals(foundUser.getLastName(), user.getLastName());
        assertEquals(foundUser.getFirstName(), user.getFirstName());
        assertEquals(foundUser.getMiddleName(), user.getMiddleName());
        assertEquals(foundUser.getPassword(), user.getPassword());
        assertEquals(foundUser.getActive(), user.getActive());
    }

    @Test
    public void whenFindByUserName_thenReturnEmpty() {
        assertNull(userRepository.findByLogin("wrong name"));
    }

    @Test
    public void whenFindByID_thenReturnUser() {
        // when
        Optional<User> foundUser = userRepository.findById(user.getId());
        // then
        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }

    @Test
    public void whenFindByIDByNull_thenThrowInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            userRepository.findById(null);
        });

    }

    @Test
    public void whenFindByID_thenReturnEmpty() {
        // when
        Optional<User> foundUser = userRepository.findById(10l);
        // then
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void whenFindAll_thenReturnListOfUser() {
        //given
        User user = TestDataUtils.getUser(null, "User2", "second pass", true, UserType.ADMIN);
        entityManager.persistAndFlush(user);
        // when
        List<User> users = userRepository.findAll();
        // then
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(users.size(), 2);

    }

    @Test
    public void it_should_save_user() {
        User newUser = TestDataUtils.getUser(null, "User2", "second pass", true, UserType.SUPPORT);
        userRepository.save(newUser);
        User foundUser = userRepository.findByLogin(newUser.getLogin());

        // then
        assertNotNull(foundUser);
        assertNotNull(foundUser.getId());
        assertEquals(foundUser.getLogin(), newUser.getLogin());
        assertEquals(foundUser.getPassword(), newUser.getPassword());
        assertEquals(foundUser.getActive(), newUser.getActive());
    }

    @Test
    public void whenSaveUserWithLoginTooLong_thenThrowConstraintViolationException() {
        User user = TestDataUtils.getUser(null, "NameWithLengthMoreThen36SymbolsIsTooLongForSaving", "second pass", true, UserType.USER);
        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(user);
        });
    }

    @Test
    public void whenSaveUserWithLoginTooShortLength_thenThrowConstraintViolationException() {
        User user = TestDataUtils.getUser(null, "1", "second pass", true, UserType.SUPPORT);
        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(user);
        });
    }

    @Test
    public void whenSaveUserWithEmailWrongLength_thenThrowConstraintViolationException() {
        User user = TestDataUtils.getUser(null, "New Name", "", true, UserType.USER);
        user.setEmail("Email_With_Length_More_Then_100_Symbols_Is_Too_Long_For_Saving_And_Should_be_an_error_on_saving_attempt");
        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(user);
        });
    }

    @Test
    public void whenSaveUserWithPhoneWrongLength_thenThrowConstraintViolationException() {
        User user = TestDataUtils.getUser(null, "New Name", "", true, UserType.USER);
        user.setPhone("Phone_With_Length_More_Then_50_Symbols_Is_Too_Long");
        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(user);
        });
    }

    @Test
    public void whenSaveUserWithExistLogin_thenThrowDataIntegrityViolationException() {
        User user = TestDataUtils.getUser(null, "User", "", true, UserType.SUPPORT);
        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(user);
        });
    }

    @Test
    public void whenSaveUserWithEmptyActive_thenThrowDataIntegrityViolationException() {
        User user = TestDataUtils.getUser(null, "User", "", true, UserType.SUPPORT);
        user.setActive(null);
        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(user);
        });
    }

    @Test
    public void whenDeleteById_thenOk() {
        //given
        User user = TestDataUtils.getUser(null, "User2", "second pass", true, UserType.USER);
        entityManager.persistAndFlush(user);
        assertEquals(userRepository.findAll().size(), 2);

        User foundUser = userRepository.findByLogin("User2");

        // when
        userRepository.deleteById(foundUser.getId());
        // then
        assertEquals(userRepository.findAll().size(), 1);
    }

    @Test
    public void whenDeleteById_thenThrowEmptyResultDataAccessException() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userRepository.deleteById(10000000l);
        });
    }

}