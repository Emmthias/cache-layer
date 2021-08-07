package com.emmthias.cache.converter;

import com.emmthias.cache.common.response.success.impl.GetCacheResponse;
import com.emmthias.cache.domain.Cache;
import com.emmthias.cache.domain.CacheObject;
import com.emmthias.cache.repository.impl.ICacheRepository;
import com.emmthias.cache.repository.impl.RejectCacheRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.emmthias.cache.constants.Constants.CACHE_PREFERENCE_EVICTION_POLICY;
import static com.emmthias.cache.constants.Constants.CACHE_PREFERENCE_SLOT_NUMBER;
import static com.emmthias.cache.constants.Constants.CACHE_PREFERENCE_TTL;
import static com.emmthias.cache.constants.Constants.EVICTION_POLICY_DEFAULT_VALUE_NAME;
import static com.emmthias.cache.constants.Constants.SLOT_NUMBER_DEFAULT_VALUE;
import static com.emmthias.cache.constants.Constants.TIME_TO_LIVE_DEFAULT_VALUE;
import static com.emmthias.cache.converter.Converters.buildGetCacheResponse;
import static com.emmthias.cache.converter.Converters.convertCachePreference;
import static com.emmthias.cache.converter.Converters.convertMapIntoJsonStructure;
import static com.emmthias.cache.converter.Converters.convertMapToCacheObject;
import static com.emmthias.cache.converter.Converters.convertMapToSingleCacheObject;
import static com.emmthias.cache.converter.Converters.convertToJsonElement;
import static com.emmthias.cache.converter.Converters.mergeObjects;
import static com.emmthias.cache.utils.Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE;
import static com.emmthias.cache.utils.Constant.getEXPECTED_OBJECT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConvertersTest {

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/request/objects/add_objects.json"})
    public void whenConvertMapToCacheObject_withValue_shouldReturnList(String fileName) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(fileName)));
        Cache cache = new ObjectMapper().readValue(json, Cache.class);
        List<CacheObject> cacheObjects = convertMapToCacheObject(cache.getElements());
        assertEquals(getEXPECTED_OBJECT().size(), cacheObjects.size());

        cacheObjects.stream().forEach(p -> assertNotNull(p.getKey()));
    }

    @Test
    void whenConvertMapToCacheObject_withNULLValue_shouldReturnEmptyList() throws IOException {
        List<CacheObject> cacheObjects = convertMapToCacheObject(null);
        assertEquals(Collections.emptyList(), cacheObjects);
    }

    @Test
    void whenConvertMapToSingleCacheObject_shouldReturnJsonObject() throws IOException {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("key", "value");
        CacheObject cacheObjects = convertMapToSingleCacheObject(testMap);
        assertNotNull(cacheObjects);
    }

    @Test
    void whenMergeObjects_shouldReturnMergedJsonObject() throws IOException {
        Map<String, String> testMap = new HashMap<>();
        JSONObject currentElement = new JSONObject();
        String idProperty = "id";
        currentElement.put(idProperty, "1");
        JSONObject newProperty = new JSONObject();

        String nameProperty = "name";
        newProperty.put(nameProperty, "test");

        CacheObject newElement = new CacheObject(newProperty);

        JSONObject mergedObject = mergeObjects(currentElement, newElement);
        assertNotNull(mergedObject);
        assertEquals(2, mergedObject.toMap().size());

        // verify base element
        assertTrue(mergedObject.toMap().containsKey(idProperty));
        assertEquals(mergedObject.toMap().get(idProperty), currentElement.get(idProperty));
        // verify second element
        assertTrue(mergedObject.toMap().containsKey(nameProperty));
        assertEquals(mergedObject.toMap().get(idProperty), currentElement.get(idProperty));
    }

    @Test
    void whenConvertCachePreference_shouldReturnCachePreferenceJsonObject() throws IOException {
        JSONObject defaultCachePreference = convertCachePreference(getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        assertNotNull(defaultCachePreference);
        assertEquals(SLOT_NUMBER_DEFAULT_VALUE, defaultCachePreference.get(CACHE_PREFERENCE_SLOT_NUMBER));
        assertEquals(TIME_TO_LIVE_DEFAULT_VALUE, defaultCachePreference.get(CACHE_PREFERENCE_TTL));
        assertEquals(EVICTION_POLICY_DEFAULT_VALUE_NAME, defaultCachePreference.get(CACHE_PREFERENCE_EVICTION_POLICY));
    }

    @Test
    void WhenConvertToJsonElement_shouldReturnJsonObject() {
        String key = "key";
        String value = "value";
        JSONObject currentObject = new JSONObject().put(key, value);
        assertEquals(currentObject.toMap(), convertToJsonElement(key, value).toMap());
    }

    @Test
    void WhenBuildGetCacheResponse_shouldCacheResponse() {
        JSONObject response = new JSONObject();
        response.put("key", "value");
        GetCacheResponse cacheResponse = buildGetCacheResponse(response);
        assertNotNull(cacheResponse);
        assertEquals(response.toMap(), cacheResponse.getData().getAttributes().getPayload().getElement());
    }

    @Test
    void WhenBuildGetCacheResponse_shouldCacheCollectionResponse() {
        JSONObject response = new JSONObject();
        response.put("key", "value");
        List<JSONObject> responseList = Arrays.asList(response);

        GetCacheResponse cacheResponse = buildGetCacheResponse(responseList);
        assertNotNull(cacheResponse);
        assertEquals(responseList.size(), cacheResponse.getData().getAttributes().getPayload().getElements().size());
        assertEquals(responseList.get(0).toMap(),
                cacheResponse.getData().getAttributes().getPayload().getElements().iterator().next());
    }

    @Test
    void WhenConvertMapIntoJsonStructure_shouldCacheCollectionResponse() {
        Map<String, ICacheRepository> cacheRepositoryMap = new HashMap<>();
        RejectCacheRepository repository = new RejectCacheRepository();
        repository.setCachePreference(getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        getEXPECTED_OBJECT().forEach(e -> repository.getInstance().put(e.getKey(), e.getValue()));
        cacheRepositoryMap.put("reject policy", repository);
        JSONObject jsonObjectResponse = convertMapIntoJsonStructure(cacheRepositoryMap);

        assertNotNull(jsonObjectResponse);
    }


}