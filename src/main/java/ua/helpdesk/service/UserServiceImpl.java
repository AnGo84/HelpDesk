package ua.helpdesk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.dao.UserDao;
import ua.helpdesk.entities.User;

import java.util.List;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //@Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public User findById(Integer id) {

        return dao.findById(id);
    }

    public User findByLogin(String login) {
        User user = dao.findByLogin(login);
        return user;
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateUser(User user) {
        User entity = dao.findById(user.getId());
        if (entity != null) {
            entity.setLogin(user.getLogin());
            if (!user.getPassword().equals(entity.getPassword())) {
                entity.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            entity.setLastName(user.getLastName());
            entity.setFirstName(user.getFirstName());
            entity.setMiddleName(user.getMiddleName());
            entity.setEmail(user.getEmail());
            entity.setPhone(user.getPhone());
//			entity.setUserProfiles(user.getUserProfiles());
            entity.setUserType(user.getUserType());
            entity.setGroups(user.getGroups());
            entity.setActive(user.getActive());
        }
    }


    public void deleteUserByLogin(String login) {
        dao.deleteByLogin(login);
    }

    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }

    public boolean isUserLoginUnique(Integer id, String login) {
        User user = findByLogin(login);
        return (user == null || ((id != null) && (user.getId() == id)));
    }

}
