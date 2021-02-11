package ua.helpdesk.repository;

import org.springframework.stereotype.Repository;
import ua.helpdesk.entity.Category;

@Repository
public interface CategoryRepository extends CommonRepository<Category> {
    Category findByName(String name);
}
