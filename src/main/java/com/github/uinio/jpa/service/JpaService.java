package com.github.uinio.jpa.service;


import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @param <T>  record
 * @param <ID> Primary key Type
 * @author uinio
 */
public interface JpaService<T, ID> {

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.JpaRepository#flush()
     */
    void flush();

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.repository.CrudRepository#save(Object)
     */
    T save(T entity);

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.repository.CrudRepository#saveAll(Iterable)
     */
    List<T> saveAll(Collection<T> entities);

    /**
     * Update based on primary key Only support single table update
     * @return count
     */
    int update(T entity);

    /**
     * updateAll Only support single table update
     * @return count
     */
    int updateAll(Collection<T> entities);

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.repository.CrudRepository#deleteAll()
     */
    void deleteAll();


    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.repository.CrudRepository#deleteById(Object)
     */
    void deleteById(ID id);

    /**
     * deleteByIds
     *
     * @return count
     */
    int deleteByIds(ID[] ids);

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.JpaRepository#getOne(Object)
     */
    T getOne(ID id);

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.JpaRepository#findById(Object)
     */
    Optional<T> findById(ID id);


    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.JpaRepository#findAllById(Iterable)
     */
    List<T> findAllById(Collection<ID> ids);

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
     */
    List<T> findAll();

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.JpaRepository#findAll(Pageable)
     */
    Map<String, Object> page(int pageNum, int pageSize);

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.JpaRepository#existsById(Object)
     */
    boolean existsById(ID id);

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.data.jpa.repository.JpaRepository#count()
     */
    long count();

}
