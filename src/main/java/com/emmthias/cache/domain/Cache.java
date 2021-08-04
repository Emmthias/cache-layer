package com.emmthias.cache.domain;

import java.util.List;
import java.util.Map;

public class Cache {
    CachePreference cachePreference;
    List<Map<String,String>> elements;
    Map<String,String> element;

    public CachePreference getCachePreference() {
        return cachePreference;
    }

    public void setCachePreference(CachePreference cachePreference) {
        this.cachePreference = cachePreference;
    }

    public List<Map<String, String>> getElements() {
        return elements;
    }

    public void setElements(List<Map<String, String>> elements) {
        this.elements = elements;
    }

    public Map<String, String> getElement() {
        return element;
    }

    public void setElement(Map<String, String> element) {
        this.element = element;
    }
}
