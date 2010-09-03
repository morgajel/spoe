package com.morgajel.spoe.dao.support;

import java.util.List;

/**
 * Generic Data Access Object interface.
 * This Object Interface is used by all DAOs, providing a base level of functionality.
 */
public interface GenericDao<T> {

    /**
     * T is a primary key or unique field that for identifying a Dao
     * 
     * @param model a T instance having a primary key or a unique field set
     * @return the corresponding T persistent instance or null if none could be found.
     */
    T get(T model);

    /**
     * Saves or updates the passed list of T models to the repository.
     * 
     * @param models the list of models to be saved or updated.
     */
    void save(List<T> models);

    /**
     * Delete from the repository the passed list of T models.
     * @param models the list of models to be deleted.
     */    
    void delete(List<T> models);

    /**
     * Find and load a list of T instance.
     * 
     * @param model a sample model whose non-null properties may be used as search hints
     * @param searchTemplate carries additional search information
     * @return the models matching the search.
     */
    List<T> find(T model, SearchTemplate searchTemplate);

    /**
     * Count the number of T instances.
     * 
     * @param model a sample model whose non-null properties may be used as search hint
     * @param searchTemplate carries additional search information
     * @return the number of models matching the search.
     */
    int findCount(T model, SearchTemplate searchTemplate);
}
