package com.dewpoint.rts.dao;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

/**
 * Base interface providing standard CRUD features
 * and custom Search capabilities
 */
public interface Dao<E, PK extends Serializable> {
    /**
     * Get the class of an Entity
     *
     * @return the class
     */
    public Class<E> getEntityClass();

    /**
     * Find an entity by its primary key
     *
     * @param id
     *            the primary key
     * @return the entity
     */
    public E findOne(final PK id);

    /**
     * Load all entities
     *
     * @return the list of entities
     */
    public List<E> findAll();

    /**
     * Load entities based on search entity
     *
     * @return the list of entities
     */
    public List<E> findByEntity(final E searchInstance);

    /**
     * Find using named query
     *
     * @param queryName
     *            the name of the query
     * @param params
     *            the query parameters
     * @return the list of entities
     */
    List<E> findByNamedQuery(final String queryName, Object... params);

    /**
     * Find using a named query and named parameters
     *
     * @param queryName
     *            the name of the query
     * @param params
     *            the query parameters
     * @return the list of entities
     */
    List<E> findByNamedQueryAndNamedParams(final String queryName, final Map<String, ? extends Object> params);

    /**
     * Count all entities
     *
     * @return the number of entities
     */
    int countAll();

    /**
     * Count entities based on search
     *
     * @param searchInstance
     *            the search criteria
     * @return the number of entities
     */
    int countBySearch(final E searchInstance);

    /**
     * Save an entity
     *
     * @param entity
     *            the entity to save
     */
    public void create(final E entity);

    /**
     * Update an entity
     *
     * @param entity
     *            the entity to update
     */
    public void update(final E entity);

    /**
     * Delete an entity
     *
     * @param entity
     *            the entity to delete
     */
    public void delete(E entity);

    /**
     * Delete an entity based on Primary Key Id
     *
     * @param id
     *            primary key id
     */
    public void deleteById(final PK id);

    /**
     * Setter for EntityManager DI
     *
     * @param entityManager
     *            an instance of entity manager
     */
    void setEntityManager(EntityManager entityManager);
}