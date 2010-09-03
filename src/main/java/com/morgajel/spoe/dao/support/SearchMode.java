/*
 * Generated by http://www.springfuse.com
 */
package com.morgajel.spoe.dao.support;



/**
 * Static values to use in conjunction with SearchTemplate object.<br>
 * It defines the kind of search you can do in SQL.
 */
public enum SearchMode {
    ALL("all"),
    EQUALS("eq"),
    ANYWHERE("any"),
    STARTING_LIKE("sl"),
    ENDING_LIKE("el");

    private String code;

    SearchMode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static final SearchMode convert(String code) {
        for (SearchMode searchMode : SearchMode.values()) {
            if (searchMode.getCode().equals(code)) {
                return searchMode;
            }
        }

        return EQUALS; // default
    }

}
