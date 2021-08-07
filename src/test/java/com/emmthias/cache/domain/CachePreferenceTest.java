package com.emmthias.cache.domain;

import com.emmthias.cache.utils.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CachePreferenceTest {

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/cachePreference/cache_preference.json",
            "src/test/resources/cachePreference/cache_preference_only_eviction_policy.json",
            "src/test/resources/cachePreference/cache_preference_only_slot_number.json",
            "src/test/resources/cachePreference/cache_preference_only_time_to_live.json",
            "src/test/resources/cachePreference/cache_preference_without_elements.json"})
    public void cachePreferenceDeserialize_ShouldReturnCachePreferenceObject(String fileName) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(fileName)));
        Cache cacheObject = new ObjectMapper().readValue(json, Cache.class);

        assertNotNull(cacheObject);
        assertNotNull(cacheObject.toString());
    }
}