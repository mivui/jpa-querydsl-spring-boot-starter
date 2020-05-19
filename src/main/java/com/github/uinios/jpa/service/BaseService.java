package com.github.uinios.jpa.service;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseService<T, ID> {
    /**
     * Paging query
     *
     * @param pageNum  currentPage
     * @param pageSize numberOfDataPerPage
     * @param entity   rootObjectInformationForPreciseSearch
     * @return Map
     */

    Map<String, Object> page(int pageNum, int pageSize, T entity);

    /**
     * pagingQuery
     *
     * @param pageNum  currentPage
     * @param pageSize numberOfDataPerPage
     * @return Map
     */
    Map<String, Object> page(int pageNum, int pageSize);

    /**
     * queryAll
     *
     * @return all
     */
    List<T> findAll();

    /**
     * According to the primary key query, no exception will be thrown if not found
     *
     * @param id primaryKey
     * @return T
     */
    Optional<T> findById(ID id);

    /**
     * saveEntity
     *
     * @param entity entity
     * @return entity
     */
    T save(T entity);

    /**
     * addInBulk
     *
     * @param entities entities
     * @return saveContent
     */
    List<T> saveInBatch(Collection<T> entities);

    /**
     * deleteObject
     *
     * @param entity entity
     */
    void delete(T entity);

    /**
     * deleteObjectsInBulk
     *
     * @param entities entities
     */
    void deleteInBatch(List<T> entities);

    /**
     * batchDeleteAccordingToId
     *
     * @param ids ids
     */
    void deleteInBatch(ID[] ids);

    /**
     * deleteAccordingToThePrimaryKey
     *
     * @param key PrimaryKey
     */
    void deleteById(ID key);

    /**
     * Update the entity (the entity member variable is null will not be updated)
     *
     * @param entity entity
     * @return entity
     */
    T update(T entity, ID id);

    /**
     * updateObjectsInBatch
     *
     * @param entities entities
     */
    void updateInBatch(List<T> entities, ID[] ids);

}
