package ua.helpdesk.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ua.helpdesk.entity.Category;
import ua.helpdesk.repository.CategoryRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CategoryServiceImpl extends AbstractService<Category, CategoryRepository> {

    public CategoryServiceImpl(CategoryRepository repository) {
        super(repository);
    }

    public Category getByName(String name) {
        return repository.findByName(name);
    }

    public Boolean isExistByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return false;
        }
        return repository.findByName(name) != null;
    }

    public List<Category> findByAppService(Long serviceId) {
        List<Category> allCategories = getAll();
        if (CollectionUtils.isEmpty(allCategories)) {
            return Collections.emptyList();
        }
        return allCategories.stream().filter(category -> category.getAppService().getId().equals(serviceId)).collect(Collectors.toList());
    }

    @Override
    public Boolean isExist(Category entity) {
        log.info("Check on exist: {}", entity);
        if (entity == null) {
            return false;
        }
        Category foundEntity = repository.findByName(entity.getName());
        if (foundEntity == null) {
            return false;
        } else if (entity.getId() == null || !entity.getId().equals(foundEntity.getId())) {
            return true;
        }
        return false;
    }
}
