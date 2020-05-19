package com.github.uinios.jpa.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public abstract class BaseServiceImpl<T, ID> implements BaseService<T, ID> {

    @Autowired
    private JpaRepository<T, ID> jpaRepository;

    /**
     * conditionalPagingQuery
     *
     * @param pageNum  currentPage
     * @param pageSize numberOfDataPerPage
     * @param entity   rootObjectInformationForPreciseSearch
     * @return map集合
     */
    @Override
    public Map<String, Object> page(int pageNum, int pageSize, T entity) {
        Map<String, Object> map = new HashMap<>();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        //查询的实例（条件）
        Example<T> example = Example.of(entity);
        Page<T> page = jpaRepository.findAll(example, pageable);
        map.put(PageEnum.PAGE_TOTAL.getValue(), page.getTotalElements());
        map.put(PageEnum.PAGE_CONTENT.getValue(), page.getContent());
        return map;
    }

    /**
     * pagingQueryAll
     *
     * @param pageNum  currentPage
     * @param pageSize numberOfDataPerPage
     * @return mapCollection
     */
    @Override
    public Map<String, Object> page(int pageNum, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        //Query instance (condition)
        Page<T> page = jpaRepository.findAll(pageable);
        map.put(PageEnum.PAGE_TOTAL.getValue(), page.getTotalElements());
        map.put(PageEnum.PAGE_CONTENT.getValue(), page.getContent());
        return map;
    }

    /**
     * checkAll
     *
     * @return listCollection
     */
    @Override
    public List<T> findAll() {
        return jpaRepository.findAll();
    }

    /**
     * Single condition query based on primary key, no exception will not be thrown if not found
     *
     * @param id id
     * @return Optional
     */
    @Override
    public Optional<T> findById(ID id) {
        return jpaRepository.findById(id);
    }

    /**
     * save
     *
     * @param entity object
     * @return domain
     */
    @Override
    @Transactional
    public T save(T entity) {
        return jpaRepository.save(entity);
    }

    /**
     * addInBulk
     *
     * @param entity entity
     * @return List
     */
    @Override
    @Transactional
    public List<T> saveInBatch(Collection<T> entity) {
        return jpaRepository.saveAll(entity);
    }

    /**
     * deleteObject
     *
     * @param entity object
     */
    @Override
    @Transactional
    public void delete(T entity) {
        jpaRepository.delete(entity);
    }

    /**
     * deleteById
     *
     * @param key id
     */
    @Override
    @Transactional
    public void deleteById(ID key) {
        jpaRepository.deleteById(key);
    }

    /**
     * batchDeleteAccordingToId
     *
     * @param ids ids
     */
    @Override
    @Transactional
    public void deleteInBatch(ID[] ids) {
        List<T> list = new ArrayList<>();
        for (ID id : ids) {
            final Optional<T> entity = jpaRepository.findById(id);
            entity.ifPresent(list::add);
        }
        if (!list.isEmpty()) {
            jpaRepository.deleteInBatch(list);
        }
    }

    /**
     * deleteObjectsInBulk
     *
     * @param entities entities
     */
    @Override
    @Transactional
    public void deleteInBatch(List<T> entities) {
        if (Objects.nonNull(entities) && !entities.isEmpty()) {
            jpaRepository.deleteInBatch(entities);
        }
    }

    /**
     * update
     *
     * @param entity updateParameters
     * @param id     Need to filter null values based on id
     * @return updatedResults
     */
    @Override
    @Transactional
    public T update(T entity, ID id) {
        final Optional<T> object = jpaRepository.findById(id);
        if (object.isPresent()) {
            final T search = object.get();
            //search:databaseQueryObjects
            //entity:objectToBeUpdated
            // Copy the search attributes to the entity, search ignores the attributes that need to be updated in the foreground, and assign the remaining attributes to the entity
            BeanUtils.copyProperties(search, entity, IgnoreProperties.ignoreProperties(entity));
            return jpaRepository.save(entity);
        }
        return null;
    }

    /**
     * batchUpdate
     *
     * @param entities entities
     * @param ids      ids
     */
    @Override
    @Transactional
    public void updateInBatch(List<T> entities, ID[] ids) {
        List<T> list = new ArrayList<>();
        if (Objects.nonNull(entities) && !entities.isEmpty()) {
            for (T entity : entities) {
                for (ID id : ids) {
                    Optional<T> object = jpaRepository.findById(id);
                    if (object.isPresent()) {
                        final T search = object.get();
                        BeanUtils.copyProperties(search, entity, IgnoreProperties.ignoreProperties(entity));
                        list.add(entity);
                    }
                }
            }
            if (!list.isEmpty()) {
                jpaRepository.saveAll(list);
            }
        }
    }
}
