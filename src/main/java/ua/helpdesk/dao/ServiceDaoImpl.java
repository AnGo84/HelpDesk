package ua.helpdesk.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ua.helpdesk.model.Service;

import java.util.List;

@Repository("serviceDao")
public class ServiceDaoImpl extends AbstractDao<Integer, Service> implements TableDateDao<Service> {

    static final Logger logger = LoggerFactory.getLogger(ServiceDaoImpl.class);

    @Override
    public Service findById(Integer id) {
        logger.info("FindByID : {}", id);
        Service service = getByKey(id);
        return service;
    }

    @Override
    public Service findByName(String name) {
        logger.info("FindByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        Service service = (Service) criteria.uniqueResult();

        return service;
    }

    @Override
    public void save(Service service) {
        logger.info("Save : {}", service.toString());
        persist(service);
    }

    @Override
    public void deleteByName(String name) {
        logger.info("DeleteByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        Service service = (Service) criteria.uniqueResult();
        delete(service);
    }

    @Override
    public void deleteById(Integer id) {
        logger.info("DeleteByID : {}", id);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", id));
        Service service = (Service) criteria.uniqueResult();
        delete(service);
    }

    @Override
    public List<Service> findAllData() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("name"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List< Service> services = (List< Service>) criteria.list();
        return services;
    }

}
