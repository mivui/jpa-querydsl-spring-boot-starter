package com.github.uinios.jpa.service;

import com.github.uinios.jpa.exception.JpaServiceException;
import com.github.uinios.jpa.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.uinios.jpa.utils.IdFieldUtils.getIdFieldName;
import static com.github.uinios.jpa.utils.IdFieldUtils.getIdFieldValue;


/**
 * (non-Javadoc)
 *
 * @param <T>  entity
 * @param <ID> Primary key Type
 * @author Jingle-Cat
 * @see JpaService
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public abstract class JpaServiceImpl<T, ID> implements JpaService<T, ID> {

    private final Class<T> clazz;

    public JpaServiceImpl() {
        Type type = this.getClass().getGenericSuperclass();
        Type[] types = (( ParameterizedType ) type).getActualTypeArguments();
        clazz = ( Class<T> ) types[0];
    }

    @Autowired
    private JpaRepository<T, ID> jpaRepository;

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    @Transactional
    public void flush() {
        jpaRepository.flush();
    }

    @Override
    @Transactional
    public T save(T entity) {
        return jpaRepository.save(entity);
    }

    @Override
    @Transactional
    public List<T> saveAll(Iterable<T> entities) {
        return jpaRepository.saveAll(entities);
    }

    @Override
    @Transactional
    public int update(T entity) {
        Optional<String> idFieldName = getIdFieldName(clazz);
        if (idFieldName.isPresent()) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaUpdate<T> update = builder.createCriteriaUpdate(clazz);
            Root<T> root = update.from(clazz);
            Map<String, Object> properties = EntityUtils.getProperties(entity);
            properties.forEach((name, value) -> {
                Path<Object> path = root.get(name);
                update.set(path, value);
            });
            Optional<Object> idFieldValue = getIdFieldValue(entity, idFieldName.get());
            if (idFieldValue.isPresent()) {
                Path<Object> idPath = root.get(idFieldName.get());
                update.where(builder.equal(idPath, idFieldValue.get()));
                return entityManager.createQuery(update).executeUpdate();
            } else {
                throw new JpaServiceException("The primary key cannot be null");
            }

        }
        return 0;
    }

    @Override
    @Transactional
    public int updateAll(List<T> entities) {
        int count = 0;
        if (Objects.nonNull(entities) && !entities.isEmpty()) {
            for (T entity : entities) {
                count += this.update(entity);
            }
        }
        return count;
    }

    @Override
    @Transactional
    public void deleteAll() {
        jpaRepository.deleteAllInBatch();
    }


    @Override
    @Transactional
    public void deleteById(ID id) {
        if (Objects.isNull(id)) {
            throw new JpaServiceException("The primary key cannot be null");
        }
        jpaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public int deleteByIds(ID[] ids) {
        if (Objects.isNull(ids)) {
            throw new JpaServiceException("The primary key cannot be null");
        }
        Optional<String> idFieldName = getIdFieldName(clazz);
        if (idFieldName.isPresent()) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaDelete<T> delete = builder.createCriteriaDelete(clazz);
            Root<T> root = delete.from(clazz);
            List<ID> idList = Stream.of(ids).collect(Collectors.toList());
            delete.where(root.get(idFieldName.get()).in(idList));
            return entityManager.createQuery(delete).executeUpdate();
        }
        return 0;
    }

    @Override
    public T getOne(ID id) {
        return jpaRepository.getOne(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        return jpaRepository.findAllById(ids);
    }

    @Override
    public List<T> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Map<String, Object> page(int pageNum, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<T> page = jpaRepository.findAll(pageable);
        map.put(PageResult.total(), page.getTotalElements());
        map.put(PageResult.content(), page.getContent());
        return map;
    }

    @Override
    public boolean existsById(ID id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

}
