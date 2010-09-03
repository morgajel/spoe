package com.morgajel.spoe.dao.support;

import java.io.Serializable;

import java.util.Map;
import java.util.HashMap;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.morgajel.spoe.util.AssertUtil;
import com.morgajel.spoe.util.StringUtil;

/**
 * The search template holds meta information for the dao/manager find operations<br>
 * It has two main advantages:
 * <ul>
 * <li>It simplifies the development of the manager by preventing combinatory explosion of the parameters.</li>
 * <li>It helps adding new parameters without breaking existing managers.</li>
 * </ul>
 */
public class SearchTemplate implements Serializable {
    static final private long serialVersionUID = 1L;

    public SearchTemplate() {
    }

    /**
     * Constructs a new search template and initializes it with the values
     * of the passed search template.
     */
    public SearchTemplate(SearchTemplate searchTemplate) {
        setNamedQuery(searchTemplate.getNamedQuery());
        setSearchPattern(searchTemplate.getSearchPattern());
        setCaseSensitive(searchTemplate.isCaseSensitive());
        setOrderBy(searchTemplate.getOrderBy());
        setOrderDesc(searchTemplate.isOrderDesc());
        setFirstResult(searchTemplate.getFirstResult());
        setMaxResults(searchTemplate.getMaxResults());
        setSearchMode(searchTemplate.getSearchMode());
        setCacheable(searchTemplate.isCacheable());
        setCacheRegion(searchTemplate.getCacheRegion());
        setParameters(searchTemplate.getParameters());
    }


    //-----------------------------------
    // Named query support
    //-----------------------------------
    private String namedQuery;
    private Map<String, Object> parameters = new HashMap<String, Object>();

    /**
     * Indicates to the DAO layer that a named query must be used.
     */
    public boolean hasNamedQuery() {
        return StringUtil.hasLength(namedQuery);
    }

    /**
     * Specify the named query to be used by the DAO layer.
     * Null by default.
     *
     * @param namedQuery
     */
    public SearchTemplate setNamedQuery(String namedQuery) {
        this.namedQuery = namedQuery;
        return this;
    }

    /**
     * Return the named query to be used by the DAO layer.
     */
    public String getNamedQuery() {
        return namedQuery;
    }

    /**
     * Set the parameters for the named query.
     */
    public SearchTemplate setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
        return this;
    }

    /**
     * Get the parameters for the named query.
     */
    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * Add a parameter for the named query.
     */
    public SearchTemplate addParameter(String key, Object value) {
        if (parameters == null) {
            parameters = new HashMap<String, Object>();
        }

        parameters.put(key, value);
        return this;
    }

    /**
     * Get a parameter for the named query.
     */
    public Object getParameter(String key) {
        return parameters.get(key);
    }

    
    //-----------------------------------
    // Search pattern support
    //-----------------------------------
    private String searchPattern;

    /**
     * Indicates to the DAO layer that a search pattern must be used.
     */
    public boolean hasSearchPattern() {
        return StringUtil.hasLength(searchPattern);
    }

    /**
     * Set the string value to be compared against all string columns.
     * Null by default.
     *
     * @param searchPattern
     */
    public SearchTemplate setSearchPattern(String searchPattern) {
        this.searchPattern = searchPattern;
        return this;
    }

    /**
     * Return the search pattern to be used by the DAO layer.
     */
    public String getSearchPattern() {
        return searchPattern;
    }


    //-----------------------------------
    // Case sensitiveness support
    //-----------------------------------
    private boolean caseSensitive = true;

    /**
     * Set the case sensitiveness.
     * Defaults to true.
     *
     * @param caseSensitive
     */
    public SearchTemplate setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
        return this;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }


    //-----------------------------------
    // Order by support
    //-----------------------------------
    private String orderBy;

    public boolean hasOrderBy() {
        return StringUtil.hasLength(orderBy);
    }

    /**
     * Specify that results must be ordered by the passed column
     * Null by default.
     *
     * @param orderBy
     */
    public SearchTemplate setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public String getOrderBy() {
        return this.orderBy;
    }


    //-----------------------------------
    // Order ASC/DESC support
    //-----------------------------------
    private boolean orderDesc = true;

    /**
     * Set the order descending.
     * Default to true.
     *
     * @param orderDesc true for DESC order, false for ASC order.
     */
    public SearchTemplate setOrderDesc(boolean orderDesc) {
        this.orderDesc = orderDesc;
        return this;
    }

    public boolean isOrderDesc() {
        return orderDesc;
    }


    //-----------------------------------
    // Pagination support
    //-----------------------------------
    private int maxResultsLimit = 500;
    private int maxResults = 100;

    public SearchTemplate setMaxResults(int maxResults) {
        AssertUtil.isTrue(maxResults > 0, "maxResults must be > 0");
        this.maxResults = Math.min(maxResults, maxResultsLimit);
        return this;
    }

    public int getMaxResults() {
        return maxResults;
    }

    // the first result
    private int firstResult = 0;

    public SearchTemplate setFirstResult(int firstResult) {
        AssertUtil.isTrue(firstResult >= 0, "maxResults must be >= 0");
        this.firstResult = firstResult;
        return this;
    }

    public int getFirstResult() {
        return this.firstResult;
    }


    //-----------------------------------
    // Search mode support
    //-----------------------------------
    private SearchMode searchMode = SearchMode.EQUALS;

    /**
     * Defaults to SearchMode.EQUALS.
     *
     * @param searchMode
     */
    public SearchTemplate setSearchMode(SearchMode searchMode) {
        AssertUtil.notNull(searchMode, "search mode must not be null");
        this.searchMode = searchMode;
        return this;
    }

    public SearchMode getSearchMode() {
        return searchMode;
    }


    //-----------------------------------
    // Caching support
    //-----------------------------------
    private Boolean cacheable = true;

    /**
     * Default to true.
     *
     * @param cacheable true to enable caching, false to disable caching or null to let the DAO layer decide.
     */
    public SearchTemplate setCacheable(Boolean cacheable) {
        this.cacheable = cacheable;
        return this;
    }

    /**
     * When not null, indicates if the DAO layer should cache or not the search results.
     * If null is returned, it is up to the DAO layer to decide what to do.
     */
    public Boolean isCacheable() {
        return cacheable;
    }

    /**
     * cache region
     */
    private String cacheRegion;

    public boolean hasCacheRegion() {
        return StringUtil.hasLength(cacheRegion);
    }

    public SearchTemplate setCacheRegion(String cacheRegion) {
        this.cacheRegion = cacheRegion;
        return this;
    }

    public String getCacheRegion() {
        return cacheRegion;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
