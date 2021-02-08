package ua.helpdesk.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.helpdesk.entities.Priority;

import java.util.List;

//@Repository("priorityDao")
public class PriorityDaoImpl extends AbstractDao<Integer, Priority> implements TableDateDao<Priority> {

    static final Logger logger = LoggerFactory.getLogger(PriorityDaoImpl.class);

    @Override
    public Priority findById(Integer id) {
        logger.info("FindByID : {}", id);
        Priority priority = getByKey(id);
        return priority;
    }

    @Override
    public Priority findByName(String name) {
        logger.info("FindByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        Priority priority = (Priority) criteria.uniqueResult();

        return priority;
    }

    @Override
    public void save(Priority priority) {
        logger.info("Save : {}", priority.toString());
        persist(priority);
    }

    @Override
    public void deleteByName(String name) {
        logger.info("DeleteByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        Priority priority = (Priority) criteria.uniqueResult();
        delete(priority);
    }

    @Override
    public void deleteById(Integer id) {
        logger.info("DeleteByID : {}", id);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", id));
        Priority priority = (Priority) criteria.uniqueResult();
        delete(priority);
    }

    @Override
    public List<Priority> findAllData() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("name"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<Priority> priorities = (List<Priority>) criteria.list();
        return priorities;
    }

}
