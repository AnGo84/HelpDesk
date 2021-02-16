package ua.helpdesk.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.helpdesk.entity.AppService;

import java.util.List;

//@Repository("serviceDao")
public class ServiceDaoImpl extends AbstractDao<Long, AppService> implements TableDateDao<AppService> {

	static final Logger logger = LoggerFactory.getLogger(ServiceDaoImpl.class);

	@Override
	public AppService findById(Long id) {
		logger.info("FindByID : {}", id);
		AppService service = getByKey(id);
		return service;
	}

	@Override
	public AppService findByName(String name) {
		logger.info("FindByName : {}", name);
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name));
		AppService service = (AppService) criteria.uniqueResult();

		return service;
	}

	@Override
	public void save(AppService service) {
		logger.info("Save : {}", service.toString());
		persist(service);
	}

    @Override
    public void deleteByName(String name) {
        logger.info("DeleteByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
		AppService service = (AppService) criteria.uniqueResult();
        delete(service);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("DeleteByID : {}", id);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", id));
		AppService service = (AppService) criteria.uniqueResult();
        delete(service);
    }

	@Override
	public List<AppService> findAllData() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("name"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<AppService> services = (List<AppService>) criteria.list();
		return services;
	}

}
