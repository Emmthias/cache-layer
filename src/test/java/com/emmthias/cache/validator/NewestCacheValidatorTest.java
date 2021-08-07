package com.emmthias.cache.validator;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NewestCacheValidatorTest {
    @Test
    public void whenNewestCacheValidator_shouldRemoveElements() throws IOException {
        Map<String, String> currentMap = new HashMap<>();
        currentMap.put("key", "value");

        List<String> elementsToRemove = Arrays.asList("key");

        NewestCacheValidator.INSTANCE.applyEvictionPolicy(currentMap, elementsToRemove);

        assertTrue(currentMap.isEmpty());
    }
}