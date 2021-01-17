package ua.helpdesk.dao;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ua.helpdesk.entities.User;

import java.util.List;


@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    public User findById(Integer id) {
        logger.info("User ID : {}", id);
        User user = getByKey(id);
        if (user != null) {
            //Hibernate.initialize(user.getUserProfiles());
            Hibernate.initialize(user.getUserType());
            Hibernate.initialize(user.getGroups());

        }
        return user;
    }

    public User findByLogin(String login) {
        logger.info("Login : {}", login);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("login", login));
        User user = (User) criteria.uniqueResult();
        if (user != null) {
            //Hibernate.initialize(user.getUserProfiles());
            Hibernate.initialize(user.getUserType());
            Hibernate.initialize(user.getGroups());
        }
        return user;
    }

    @SuppressWarnings("unchecked")
    public List<User> findAllUsers() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("lastName"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<User> users = (List<User>) criteria.list();

        // No need to fetch userProfiles since we are not showing them on list page. Let them lazy load.
        // Uncomment below lines for eagerly fetching of userProfiles if you want.
        /*
		for(User user : users){
			Hibernate.initialize(user.getUserProfiles());
		}*/
        return users;
    }

    public void save(User user) {
        persist(user);
    }

    @Override
    public void deleteByLogin(String login) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("login", login));
        User user = (User) crit.uniqueResult();
        delete(user);
    }

}
