package com.github.uinio.jpa.service;

import com.github.uinio.jpa.utils.EntityUtils;
import com.github.uinio.jpa.utils.KeyUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * (non-Javadoc)
 *
 * @param <T>  entity
 * @param <ID> Primary key Type
 * @author uinio
 * @see JpaService
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public abstract class JpaServiceImpl<T, ID> implements JpaService<T, ID> {

    private final Class<T> clazz;

    @SuppressWarnings("unchecked")
    public JpaServiceImpl() {
        ParameterizedType parameterizedType = ( ParameterizedType ) this.getClass().getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        clazz = ( Class<T> ) types[0];
    }

    @PersistenceContext
    protected EntityManager entityManager;

    private JpaRepository<T, ID> jpaRepository;

    protected JPAQueryFactory jpaQueryFactory;

    @Autowired
    public void setJpaRepository(JpaRepository<T, ID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Autowired
    public void setJpaQueryFactory(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    @Transactional
    public void flush() {
        jpaRepository.flush();
    }

    @Override
    @Transactional
    public T save(T entity) {
        Assert.notNull(entity, "The given entity must not be null!");
        return jpaRepository.save(entity);
    }

    @Override
    @Transactional
    public List<T> saveAll(Collection<T> entities) {
        Assert.notEmpty(entities, "The given entities must not be null!");
        return jpaRepository.saveAll(entities);
    }

    @Override
    @Transactional
    public int update(T entity) {
        String idFieldName = KeyUtils.fieldName(clazz);
        Assert.notNull(idFieldName, "the current table cannot get the (javax.persistence.Id) primary key !");
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<T> update = builder.createCriteriaUpdate(clazz);
        Root<T> root = update.from(clazz);
        Map<String, Object> properties = EntityUtils.getProperties(entity);
        properties.forEach((name, value) -> update.set(root.get(name), value));
        Object fieldValue = KeyUtils.fieldValue(entity);
        Assert.notNull(fieldValue, "The given entity must not be null!");
        update.where(builder.equal(root.get(idFieldName), fieldValue));
        return entityManager.createQuery(update).executeUpdate();
    }

    @Override
    @Transactional
    public int updateAll(Collection<T> entities) {
        AtomicInteger count = new AtomicInteger(0);
        Assert.notEmpty(entities, "The given entities must not be null!");
        entities.forEach(o -> {
            count.getAndIncrement();
            this.update(o);
        });
        return count.get();
    }

    @Override
    @Transactional
    public void deleteAll() {
        jpaRepository.deleteAllInBatch();
    }


    @Override
    @Transactional
    public void deleteById(ID id) {
        Assert.notNull(id, "The given id must not be null!");
        jpaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public int deleteByIds(ID[] ids) {
        Assert.notEmpty(ids, "The given id must not be null!");
        String idFieldName = KeyUtils.fieldName(clazz);
        Assert.notNull(idFieldName, "the current table cannot get the (javax.persistence.Id) primary key !");
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<T> delete = builder.createCriteriaDelete(clazz);
        Root<T> root = delete.from(clazz);
        List<ID> idList = Stream.of(ids).collect(Collectors.toList());
        delete.where(root.get(idFieldName).in(idList));
        return entityManager.createQuery(delete).executeUpdate();
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
    public List<T> findAllById(Collection<ID> ids) {
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
