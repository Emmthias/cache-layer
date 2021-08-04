package com.emmthias.cache.validator;

import java.util.List;
import java.util.Map;

public interface MapBehavior {
    void applyEvictionPolicy(Map<?, ?> data, List<String> elementsToRemove);
}
