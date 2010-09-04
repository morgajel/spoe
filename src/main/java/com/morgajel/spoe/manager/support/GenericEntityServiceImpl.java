
package com.morgajel.spoe.manager.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.morgajel.spoe.dao.support.GenericDao;
import com.morgajel.spoe.dao.support.SearchTemplate;

public abstract class GenericEntityServiceImpl<T, PK extends Serializable> implements GenericEntityService<T, PK> {

    private Log logger;

    public GenericEntityServiceImpl() {
        this.logger = LogFactory.getLog(getClass());
    }
    
    abstract public GenericDao<T> getDao();

    /**
     * Returns a new, not yet persisted, instance.
     * This method may be convenient when you need to instantiate 
     * an entity in SpringWebFlow script. 
     */
    public abstract T getNew();

    //-------------------------------------------------------------
    //  Save methods
    //-------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public void save(T model) {
        List<T> models = new ArrayList<T>(1);
        models.add(model);
        save(models);
    }

    /**
     * {@inheritDoc}
     */
    public void save(List<T> models) {
        if (logger.isDebugEnabled()) {
            logger.debug("Save List (" + models.size() + " items)");
        }

        getDao().save(models);
    }

    //-------------------------------------------------------------
    //  Get and Delete methods (primary key or unique constraints)
    //-------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public T get(T model) {               
        return getDao().get(model);
    }

    /**
     * {@inheritDoc}
     */
    public void delete(PK pk) {
        delete(get(pk));
    }
    
    public void delete(T model) {
        if (model == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping deletion for null model!");
            }
            
            return;
        }
        
        List<T> models = new ArrayList<T>(1);
        models.add(model);
        delete(models);        
    }


    /**
     * {@inheritDoc}
     */
     public void delete(List<T> models) {
        if (logger.isDebugEnabled()) {
            logger.debug("Delete List (" + models.size() + " items)");
        }

        getDao().delete(models);
    }

    //-------------------------------------------------------------
    //  Finders methods
    //-------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public T findUnique(T model) {
        return findUnique(model, new SearchTemplate());
    }

    /**
     * {@inheritDoc}
     */
    public T findUnique(T model, SearchTemplate searchTemplate) {
        T result = findUniqueOrNone(model, searchTemplate);

        if (result == null) {
            throw new InvalidDataAccessApiUsageException("Developper: You expected 1 result but we found none ! sample: " + model);
        }
        
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public T findUniqueOrNone(T model) {
        return findUniqueOrNone(model, new SearchTemplate());
    }

    /**
     * {@inheritDoc}
     */
    public T findUniqueOrNone(T model, SearchTemplate searchTemplate) {
        // this code is an optimization to prevent using a count
        // we request at most 2, if there's more than one then we throw an InvalidDataAccessApiUsageException
        SearchTemplate searchTemplateBounded = new SearchTemplate(searchTemplate);
        searchTemplateBounded.setFirstResult(0);
        searchTemplateBounded.setMaxResults(2);
        List<T> results = find(model, searchTemplateBounded);

        if (results == null || results.isEmpty()) {
            return null;
        }

        if (results.size() > 1) {
            throw new InvalidDataAccessApiUsageException("Developper: You expected 1 result but we found more ! sample: " + model);
        }

        return results.iterator().next();
    }

    /**
     * {@inheritDoc}
     */
    public List<T> find() {
        return find(getNew(), new SearchTemplate());
    }    
    
    /**
     * {@inheritDoc}
     */
    public List<T> find( T model) {
        return find(model, new SearchTemplate());
    }

    /**
     * {@inheritDoc}
     */
    public List<T> find(SearchTemplate searchTemplate) {
        return find(getNew(), searchTemplate);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<T> find(T model, SearchTemplate searchTemplate) {
       return getDao().find(model, searchTemplate);
    }

    //-------------------------------------------------------------
    //  Count methods
    //-------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public int findCount() {
        return findCount(getNew(), new SearchTemplate());
    }
    
    /**
     * {@inheritDoc}
     */
    public int findCount(T model) {
        return findCount(model, new SearchTemplate());
    }

    /**
     * {@inheritDoc}
     */
    public int findCount(SearchTemplate searchTemplate) {
        return findCount(getNew(), searchTemplate);
    }
    
    /**
     * {@inheritDoc}
     */
    public int findCount(T model, SearchTemplate searchTemplate) {
        return getDao().findCount(model, searchTemplate);
    }
}
