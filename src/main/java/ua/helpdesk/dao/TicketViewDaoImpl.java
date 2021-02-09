package ua.helpdesk.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.helpdesk.entity.TicketView;
import ua.helpdesk.entity.User;
import ua.helpdesk.service.UserService;

import java.util.List;

//@Repository("ticketViewDao")
public class TicketViewDaoImpl extends AbstractDao<Long, TicketView> implements TicketViewDao {

    static final Logger logger = LoggerFactory.getLogger(TicketViewDaoImpl.class);

    @Autowired
    UserService userService;

    @Override
    public TicketView findById(Long id) {
        logger.info("FindByID : {}", id);
        TicketView ticket = getByKey(id);
        return ticket;
    }

    @Override
    public TicketView findByName(String name) {
        logger.info("FindByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("number", name));
        TicketView ticket = (TicketView) criteria.uniqueResult();

        return ticket;
    }

    @Override
    public void save(TicketView ticket) {
        logger.info("Save : {}", ticket.toString());
//        persist(ticket);
    }

    @Override
    public void deleteByName(String name) {
        logger.info("DeleteByName : {}", name);
//        Criteria criteria = createEntityCriteria();
//        criteria.add(Restrictions.eq("number", name));
//        TicketView ticket = (TicketView) criteria.uniqueResult();
//        delete(ticket);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("DeleteByID : {}", id);
//        Criteria criteria = createEntityCriteria();
//        criteria.add(Restrictions.eq("id", id));
//        TicketView ticket = (TicketView) criteria.uniqueResult();
//        delete(ticket);
    }

    @Override
    public List<TicketView> findAllData() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("number"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<TicketView> tickets = (List<TicketView>) criteria.list();
        return tickets;
    }

    @Override
    public List<TicketView> findTicketsForUser(String userName) {
        logger.info("Find by Name: " + userName);
        User user = userService.findByLogin(userName);
        //logger.info("Find by Name: " + userID + " | USER:" + user);
        if (user == null) {
            return null;
        }
        Query query = query("from TicketView where user.id= :user_id");
        //logger.info("Query is: " + query.toString());
        query.setParameter("user_id", user.getId());

        List<TicketView> ticketViews = (List<TicketView>) query.list();
        //System.out.println("Result " + ticketViews);
        return ticketViews;
    }

    @Override
    public List<TicketView> findTicketsForUser(Long userID) {
        logger.info("Find by ID: " + userID);
        User user = userService.findById(userID);
        //logger.info("Find by ID: " + userID + " | USER:" + user);
        if (user == null) {
            return null;
        }
        Query query = query("from TicketView where user.id= :user_id");
        //logger.info("Query is: " + query.toString());
        query.setParameter("user_id", user.getId());

        List<TicketView> ticketViews = (List<TicketView>) query.list();
        //System.out.println("Result " + ticketViews);
        return ticketViews;
    }

}
