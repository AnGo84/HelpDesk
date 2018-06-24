package ua.helpdesk.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ua.helpdesk.model.UserType;


@Repository("userTypeDao")
public class UserTypeDaoImpl extends AbstractDao<Integer, UserType> implements UserTypeDao {

    public UserType findById(Integer id) {
        return getByKey(id);
    }

    public UserType findByName(String name) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("name", name));
        return (UserType) crit.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<UserType> findAll() {
        Criteria crit = createEntityCriteria();
        crit.addOrder(Order.asc("name"));
//        for (UserType userType:(List<UserType>) crit.list()
//             ) {
//            System.out.println("UserType = " + userType);
//        }
        return (List<UserType>) crit.list();
    }

}
