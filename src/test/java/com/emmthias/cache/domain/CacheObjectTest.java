package com.emmthias.cache.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static com.emmthias.cache.utils.Constant.getEXPECTED_OBJECT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CacheObjectTest {

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/request/objects/add_objects.json"})
    public void cacheObjectDeserialize_ShouldReturnCacheObject(String fileName) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(fileName)));
        Cache cache = new ObjectMapper().readValue(json, Cache.class);

        assertNotNull(cache.getElements());
        for (int i = 0; i < cache.getElements().size(); i++) {
            // compare same size
            CacheObject expectedCacheObject = getEXPECTED_OBJECT().get(i);
            Map<String, String> currentMap = cache.getElements().get(i);

            assertEquals(expectedCacheObject.getValue().length(), currentMap.size());

            for (Map.Entry<String, Object> currentSet : expectedCacheObject.getValue().toMap().entrySet()) {
                // review the key exist
                assertTrue(currentMap.containsKey(currentSet.getKey()));
                // review the same value
                assertEquals(currentSet.getValue().toString(), currentMap.get(currentSet.getKey()));
            }
        }
    }
}