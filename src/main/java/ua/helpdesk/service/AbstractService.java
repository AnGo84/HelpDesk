package ua.helpdesk.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.helpdesk.entity.AbstractEntity;
import ua.helpdesk.exception.EntityErrorType;
import ua.helpdesk.exception.EntityException;
import ua.helpdesk.repository.CommonRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractService<E extends AbstractEntity, R extends CommonRepository<E>>
        implements CommonService<E> {

    protected final R repository;

    @Override
    public E save(E entity) {
        log.info("Save entity: {}", entity);
        E savedEntity = repository.save(entity);
        return savedEntity;
    }

    @Override
    public E update(E entity) {
        return repository.save(entity);
    }

    @Override
    public E get(Long id) {
        Optional<E> entity = repository.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        return null;
    }

    @Override
    public List<E> getAll() {
        return repository.findAll();
    }

    @Override
    public Boolean deleteById(Long id) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new EntityException(String.format(EntityErrorType.ENTITY_NOT_FOUND.getDescription(), id)));
        repository.deleteById(id);
        return !repository.findById(entity.getId()).isPresent();
    }

    @Override
    public Boolean deleteAll() {
        repository.deleteAll();
        return repository.findAll().isEmpty();
    }
}
