package ua.helpdesk.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ua.helpdesk.model.Service;
import ua.helpdesk.model.TicketType;

import java.util.List;

@Repository("ticketTypeDao")
public class TicketTypeDaoImpl extends AbstractDao<Integer, TicketType> implements TableDateDao<TicketType> {

    static final Logger logger = LoggerFactory.getLogger(TicketTypeDaoImpl.class);

    @Override
    public TicketType findById(Integer id) {
        logger.info("FindByID : {}", id);
        TicketType ticketType = getByKey(id);
        return ticketType;
    }

    @Override
    public TicketType findByName(String name) {
        logger.info("FindByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        TicketType ticketType = (TicketType) criteria.uniqueResult();

        return ticketType;
    }

    @Override
    public void save(TicketType ticketType) {
        logger.info("Save : {}", ticketType);
        persist(ticketType);
    }

    @Override
    public void deleteByName(String name) {
        logger.info("DeleteByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        TicketType ticketType = (TicketType) criteria.uniqueResult();
        delete(ticketType);
    }

    @Override
    public void deleteById(Integer id) {
        logger.info("DeleteByID : {}", id);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", id));
        TicketType ticketType = (TicketType) criteria.uniqueResult();
        delete(ticketType);
    }

    @Override
    public List<TicketType> findAllData() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("name"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<TicketType> ticketTypes = (List<TicketType>) criteria.list();
        return ticketTypes;
    }

}
