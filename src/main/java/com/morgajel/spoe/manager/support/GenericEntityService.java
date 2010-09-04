
package com.morgajel.spoe.manager.support;

import java.io.Serializable;
import java.util.List;

import com.morgajel.spoe.dao.support.SearchTemplate;

public interface GenericEntityService<T, PK extends Serializable> {

    T getNew();
    
    T get(PK pk);
        
    T get(T model);

    void save(T model);
   
    void save(List<T> models);
    
    void delete(PK pk);

    void delete(T model);

    void delete(List<T> models);

    T findUnique(T model);

    T findUnique(T model, SearchTemplate searchTemplate);

    T findUniqueOrNone(T model);
    
    T findUniqueOrNone(T model, SearchTemplate searchTemplate);
    
    List<T> find();

    List<T> find(T model);

    List<T> find(SearchTemplate searchTemplate);
    
    List<T> find(T model, SearchTemplate searchTemplate);
    
    int findCount();

    int findCount(T model);

    int findCount(SearchTemplate searchTemplate);
        
    int findCount(T model, SearchTemplate searchTemplate);
}
