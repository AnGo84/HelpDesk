package ua.helpdesk.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.helpdesk.entity.TicketPriority;

import java.util.List;

//@Repository("priorityDao")
public class PriorityDaoImpl extends AbstractDao<Long, TicketPriority> implements TableDateDao<TicketPriority> {

    static final Logger logger = LoggerFactory.getLogger(PriorityDaoImpl.class);

    @Override
    public TicketPriority findById(Long id) {
        logger.info("FindByID : {}", id);
        TicketPriority ticketPriority = getByKey(id);
        return ticketPriority;
    }

    @Override
    public TicketPriority findByName(String name) {
        logger.info("FindByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        TicketPriority ticketPriority = (TicketPriority) criteria.uniqueResult();

        return ticketPriority;
    }

    @Override
    public void save(TicketPriority ticketPriority) {
        logger.info("Save : {}", ticketPriority.toString());
        persist(ticketPriority);
    }

    @Override
    public void deleteByName(String name) {
        logger.info("DeleteByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        TicketPriority ticketPriority = (TicketPriority) criteria.uniqueResult();
        delete(ticketPriority);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("DeleteByID : {}", id);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", id));
        TicketPriority ticketPriority = (TicketPriority) criteria.uniqueResult();
        delete(ticketPriority);
    }

    @Override
    public List<TicketPriority> findAllData() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("name"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<TicketPriority> priorities = (List<TicketPriority>) criteria.list();
        return priorities;
    }

}
