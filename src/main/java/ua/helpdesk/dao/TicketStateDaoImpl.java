package ua.helpdesk.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ua.helpdesk.entities.TicketState;

import java.util.List;

@Repository("ticketStateDao")
public class TicketStateDaoImpl extends AbstractDao<Integer, TicketState> implements TableDateDao<TicketState> {

    static final Logger logger = LoggerFactory.getLogger(TicketStateDaoImpl.class);

    @Override
    public TicketState findById(Integer id) {
        logger.info("FindByID : {}", id);
        TicketState ticketState = getByKey(id);
        return ticketState;
    }

    @Override
    public TicketState findByName(String name) {
        logger.info("FindByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        TicketState ticketState = (TicketState) criteria.uniqueResult();

        return ticketState;
    }

    @Override
    public void save(TicketState ticketState) {
        logger.info("Save : {}", ticketState);
        persist(ticketState);
    }

    @Override
    public void deleteByName(String name) {
        logger.info("DeleteByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        TicketState ticketState = (TicketState) criteria.uniqueResult();
        delete(ticketState);
    }

    @Override
    public void deleteById(Integer id) {
        logger.info("DeleteByID : {}", id);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", id));
        TicketState ticketState = (TicketState) criteria.uniqueResult();
        delete(ticketState);
    }

    @Override
    public List<TicketState> findAllData() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("name"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<TicketState> ticketStates = (List<TicketState>) criteria.list();
        return ticketStates;
    }

}
