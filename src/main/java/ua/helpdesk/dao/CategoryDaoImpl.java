package ua.helpdesk.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.helpdesk.entity.Category;
import ua.helpdesk.entity.Service;
import ua.helpdesk.service.TableDataService;

import java.util.List;

//@Repository("categoryDao")
//public class CategoryDaoImpl extends AbstractDao<Integer, Category> implements TableDateDao<Category> {
public class CategoryDaoImpl extends AbstractDao<Long, Category> implements CategoryDao {
    static final Logger logger = LoggerFactory.getLogger(CategoryDaoImpl.class);

    @Autowired
    TableDataService<Service> serviceService;

    @Override
    public Category findById(Long id) {
        logger.info("FindByID : {}", id);
        Category category = getByKey(id);
        return category;
    }

    @Override
    public Category findByName(String name) {
        logger.info("FindByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        Category category = (Category) criteria.uniqueResult();
        return category;
    }

    @Override
    public void save(Category category) {
        logger.info("Save : {}", category.toString());
        persist(category);

    }

    @Override
    public void deleteByName(String name) {
        logger.info("DeleteByName : {}", name);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        Category category = (Category) criteria.uniqueResult();
        delete(category);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("DeleteByID : {}", id);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", id));
        Category category = (Category) criteria.uniqueResult();
        delete(category);
    }

    @Override
    public List<Category> findAllData() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("service"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<Category> categories = (List<Category>) criteria.list();
        return categories;
    }

    @Override
    public List<Category> findCategoryForService(String serviceName){
        logger.info("Find by name: " + serviceName);
        ServiceDaoImpl serviceDao = new ServiceDaoImpl();
        Service service = serviceDao.findByName(serviceName);
        if (service==null){
            return null;
        }

        Query query = query("from Category where service= :service_id");
        query.setParameter("service_id", service);

        List<Category> categories = (List<Category>) query.list();
        return categories;
    }

    @Override
    public List<Category> findCategoryForService(Long serviceID) {
        logger.info("Find by ID: " + serviceID);

        Service service = serviceService.findById(serviceID);
        //logger.info("Find by ID: " + serviceID + " SERVICE:" + service);
        if (service == null) {
            return null;
        }

        Query query = query("from Category where service.id= :service_id");
        //logger.info("Query is: " + query.toString());
        query.setParameter("service_id", service.getId());

        List<Category> categories = (List<Category>) query.list();
        //System.out.println("Result "+categories);
        return categories;
    }
}
