package ua.helpdesk.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ua.helpdesk.model.Service;
import ua.helpdesk.model.Ticket;

import java.util.List;

@Repository("ticketDao")
public class TicketDaoImpl extends AbstractDao<Integer, Ticket> implements TableDateDao<Ticket> {

    static final Logger logger = LoggerFactory.getLogger(TicketDaoImpl.class);

    @Override
    public Ticket findById(Integer id) {
        logger.info("FindByID : {}", id);
        Ticket ticket = getByKey(id);
        return ticket;
    }

    @Override
    public Ticket findByName(String name) {
        logger.info("FindByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("number", name));
        Ticket ticket = (Ticket) criteria.uniqueResult();

        return ticket;
    }

    @Override
    public void save(Ticket ticket) {
        logger.info("Save : {}", ticket.toString());
        persist(ticket);
    }

    @Override
    public void deleteByName(String name) {
        logger.info("DeleteByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("number", name));
        Ticket ticket = (Ticket) criteria.uniqueResult();
        delete(ticket);
    }

    @Override
    public void deleteById(Integer id) {
        logger.info("DeleteByID : {}", id);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", id));
        Ticket ticket = (Ticket) criteria.uniqueResult();
        delete(ticket);
    }

    @Override
    public List<Ticket> findAllData() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("number"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<Ticket> tickets = (List<Ticket>) criteria.list();
        return tickets;
    }

}
