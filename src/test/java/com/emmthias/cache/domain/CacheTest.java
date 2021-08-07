package com.emmthias.cache.domain;

import com.emmthias.cache.utils.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CacheTest {

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/request/buckets/create_bucket_empty.json"})
    public void whenNonCachePreference_thenReflectionTestUtilsInvokeMethod(String fileName) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(fileName)));
        Cache cacheObject = new ObjectMapper().readValue(json, Cache.class);
        CachePreference mock = CachePreference.builder().build();

        ReflectionTestUtils.setField(cacheObject, "cachePreference", Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        assertEquals(Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE(), cacheObject.getCachePreference());
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/request/buckets/Rejected/create_rejected_bucket.json"})
    public void cacheDeserialize_ShouldReturnCacheObject(String fileName) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(fileName)));
        Cache cacheObject = new ObjectMapper().readValue(json, Cache.class);

        assertNotNull(cacheObject);
        assertNotNull(cacheObject.getElements());
        assertEquals(Constant.getEXPECTED_OBJECT().size(), cacheObject.getElements().size());
        assertNotNull(cacheObject.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/request/objects/patch_object.json",
            "src/test/resources/request/objects/update_object.json"})
    public void cacheElementDeserialize_ShouldReturnCacheObject(String fileName) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(fileName)));
        Cache cacheObject = new ObjectMapper().readValue(json, Cache.class);

        assertNotNull(cacheObject);
        assertNotNull(cacheObject.getElement());
    }
}