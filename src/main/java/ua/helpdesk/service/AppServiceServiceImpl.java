package ua.helpdesk.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.entity.AppService;
import ua.helpdesk.repository.AppServiceRepository;

@Service
@Transactional
@Slf4j
public class AppServiceServiceImpl extends AbstractService<AppService, AppServiceRepository> {
	public AppServiceServiceImpl(AppServiceRepository repository) {
		super(repository);
	}

	public AppService getByName(String name) {
		return repository.findByName(name);
	}

	public Boolean isExistByName(String name) {
		if (StringUtils.isEmpty(name)) {
			return false;
		}
		return repository.findByName(name) != null;
	}

	@Override
	public Boolean isExist(AppService entity) {
		log.info("Check on exist: {}", entity);
		if (entity == null) {
			return false;
		}
		AppService foundEntity = repository.findByName(entity.getName());
		if (foundEntity == null) {
			return false;
		} else if (entity.getId() == null || !entity.getId().equals(foundEntity.getId())) {
			return true;
		}
		return false;
	}
}

