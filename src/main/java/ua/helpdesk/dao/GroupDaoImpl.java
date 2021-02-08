package ua.helpdesk.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.helpdesk.entities.Group;

import java.util.List;

//@Repository("groupDao")
public class GroupDaoImpl extends AbstractDao<Integer, Group> implements TableDateDao<Group> {

    static final Logger logger = LoggerFactory.getLogger(GroupDaoImpl.class);

    @Override
    public Group findById(Integer id) {
        logger.info("FindByID : {}", id);
        Group group = getByKey(id);
        /*if (group != null) {
            Hibernate.initialize(group.getCategories());
        }*/
        return group;
    }

    @Override
    public Group findByName(String name) {
        logger.info("FindByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        Group group = (Group) criteria.uniqueResult();

        return group;
    }

    @Override
    public void save(Group group) {
        logger.info("Save : {}", group.toString());
        persist(group);
    }

    @Override
    public void deleteByName(String name) {
        logger.info("DeleteByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        Group group = (Group) criteria.uniqueResult();
        delete(group);
    }

    @Override
    public void deleteById(Integer id) {
        logger.info("DeleteByID : {}", id);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", id));
        Group group = (Group) criteria.uniqueResult();
        delete(group);
    }

    @Override
    public List<Group> findAllData() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("name"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<Group> groups = (List<Group>) criteria.list();
        return groups;
    }

}
