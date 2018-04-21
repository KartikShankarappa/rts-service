package com.dewpoint.rts.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
@Transactional
public class BaseDao<E, ID extends Serializable> extends AbstractBaseDao<E, ID> {
    private Class<E> entityClass = null;

    @PersistenceContext
    private EntityManager entityManager;

    public BaseDao() {
        try {
            String fullClassName = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            fullClassName = "com.dewpoint.rts.model." + fullClassName;
            this.entityClass = (Class<E>) Class.forName(fullClassName);
        } catch (ClassNotFoundException e) {}
    }

    public BaseDao(final Class<E> entityClass) {
        super();
        this.entityClass = entityClass;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Class<E> getEntityClass() {
        return entityClass;
    }

    @Override
    public void create(E entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void update(E entity) {
        getEntityManager().merge(entity);
    }

    @Override
    public void delete(E entity) {
        getEntityManager().remove(entity);
    }

    @Override
    public void deleteById(ID id) {
        E entity = findOne(id);
        delete(entity);
    }

    @Override
    public E findOne(ID id) {
        final E result = getEntityManager().find(entityClass, id);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> findAll() {
        Query query = getEntityManager().createQuery("from " + entityClass.getName());
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> findByEntity(E searchInstance) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> queryObj = criteriaBuilder.createQuery();
        Root<E> from = queryObj.from(entityClass);

        List<Predicate> predicates = new ArrayList<Predicate>();
        Map<String, String> fieldsAndValues = getFieldNamesAndValues(searchInstance);
        for (final Map.Entry<String, String> param : fieldsAndValues.entrySet()) {
            System.out.println("fieldname and value " + param.getKey() + "  " + param.getValue());
            predicates.add(criteriaBuilder.equal(from.get(param.getKey()), param.getValue()));
        }
        queryObj.select(from).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Object> typedQuery = entityManager.createQuery(queryObj);
        List<E> resultList = (List<E>) typedQuery.getResultList();
        return resultList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> findByNamedQuery(String queryName, Object... params) {
        Query query = getEntityManager().createNamedQuery(queryName);

        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        final List<E> result = (List<E>) query.getResultList();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> findByNamedQueryAndNamedParams(String queryName, Map<String, ? extends Object> params) {
        Query query = getEntityManager().createNamedQuery(queryName);

        for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        final List<E> result = (List<E>) query.getResultList();
        return result;
    }

    @Override
    public int countAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> queryObj = criteriaBuilder.createQuery();
        Root<E> from = queryObj.from(entityClass);
        CriteriaQuery<Object> selectQuery = queryObj.select(from);
        TypedQuery<Object> typedQuery = entityManager.createQuery(selectQuery);
        List<Object> resultList = typedQuery.getResultList();
        return resultList != null ? resultList.size() : 0;
    }

    @Override
    public int countBySearch(E searchInstance) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> queryObj = criteriaBuilder.createQuery();
        Root<E> from = queryObj.from(entityClass);
        List<Predicate> predicates = new ArrayList<Predicate>();
        Map<String, String> fieldsAndValues = getFieldNamesAndValues(searchInstance);
        for (final Map.Entry<String, String> param : fieldsAndValues.entrySet()) {
            predicates.add(criteriaBuilder.equal(from.get(param.getKey()), param.getValue()));
        }
        queryObj.select(from).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Object> typedQuery = entityManager.createQuery(queryObj);
        List<Object> resultList = typedQuery.getResultList();
        return resultList != null ? resultList.size() : 0;
    }

    /**
     * Limt to String and Long fields at the moment for generic search
     */
    @SuppressWarnings({"rawtypes"})
    private Map<String, String> getFieldNamesAndValues(final E instanceClass) {
        Map<String, String> fieldMap = new HashMap<String, String>();
        Class instanceCls = instanceClass.getClass();
        Field[] valueObjFields = instanceCls.getDeclaredFields();
        try {
            for (int i = 0; i < valueObjFields.length; i++) {
                Annotation[] at = valueObjFields[i].getAnnotations();
                System.out.print("field name itself " + valueObjFields[i].getName());

                if (valueObjFields[i].getType().getName().endsWith("java.lang.String")
                        || valueObjFields[i].getType().getName().endsWith("java.lang.Integer")
                        || valueObjFields[i].getType().getName().endsWith("java.util.Date")
                        || valueObjFields[i].getType().getName().endsWith("java.lang.Long")) {
                    valueObjFields[i].setAccessible(true);
                    Object newObj = valueObjFields[i].get(instanceClass);
                    if (newObj != null
                            && !valueObjFields[i].getName().equals("serialVersionUID")) {
                        fieldMap.put(valueObjFields[i].getName(), newObj.toString());
                        System.out.print("field name and value " + valueObjFields[i].getName() + "  -- " + newObj.toString());
                    }
                }


            }
        } catch (IllegalAccessException ex) {
            //
        }
        return fieldMap;
    }
}
