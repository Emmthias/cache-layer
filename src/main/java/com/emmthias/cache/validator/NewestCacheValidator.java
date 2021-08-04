package com.emmthias.cache.validator;

import java.util.List;
import java.util.Map;

public enum NewestCacheValidator implements MapBehavior {
    INSTANCE;

    @Override
    public void applyEvictionPolicy(Map<?, ?> data, List<String> elementsToRemove) {
        elementsToRemove.forEach(k -> data.remove(k, data.get(k)));
    }
}
