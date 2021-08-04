package com.emmthias.cache.converter;

import com.emmthias.cache.common.response.success.impl.GetCacheResponse;
import com.emmthias.cache.common.response.success.impl.GetCacheResponseAttributes;
import com.emmthias.cache.common.response.success.impl.GetCacheResponseData;
import com.emmthias.cache.common.response.success.impl.GetCacheResponsePayload;
import com.emmthias.cache.domain.CacheObject;
import com.emmthias.cache.domain.CachePreference;
import com.emmthias.cache.repository.impl.ICacheRepository;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.emmthias.cache.constants.Constants.CACHE_PREFERENCE_EVICTION_POLICY;
import static com.emmthias.cache.constants.Constants.CACHE_PREFERENCE_SLOT_NUMBER;
import static com.emmthias.cache.constants.Constants.CACHE_PREFERENCE_TTL;
import static com.emmthias.cache.constants.Constants.CACHE_RESOURCE_TYPE;
import static java.util.Objects.nonNull;

public class Converters {
    public static List<CacheObject> convertMapToCacheObject(List<Map<String, String>> data) {
        return nonNull(data) ? data.stream().map(d -> convertMapToSingleCacheObject(d)).collect(Collectors.toList()) :
                Collections.EMPTY_LIST;
    }

    public static CacheObject convertMapToSingleCacheObject(Map<String, String> d) {
        return new CacheObject(new JSONObject(d));
    }

    public static JSONObject mergeObjects(JSONObject currentElement, CacheObject newElement) {
        newElement.getValue().keySet().stream().forEach(e -> currentElement.put(e, newElement.getValue().get(e)));
        return newElement.getValue();
    }

    public static JSONObject convertMapIntoJsonStructure(Map<String, ICacheRepository> CacheRepositoryMap) {
        JSONObject currentStructure = new JSONObject();
        JSONObject currentPreferences;

        for (Map.Entry<String, ICacheRepository> currentBucket : CacheRepositoryMap.entrySet()) {
            currentPreferences = new JSONObject()
                    .put("cachePreference", convertCachePreference(currentBucket.getValue().getCachePreference()));
            currentPreferences.put("elements", currentBucket.getValue().getAllCacheObject());
            currentStructure.put(currentBucket.getKey(), currentPreferences);
        }

        return currentStructure;
    }

    public static JSONObject convertCachePreference(CachePreference cachePreference) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(CACHE_PREFERENCE_SLOT_NUMBER, cachePreference.getSlotNumber());
        jsonObject.put(CACHE_PREFERENCE_TTL, cachePreference.getTimeToLive());
        jsonObject.put(CACHE_PREFERENCE_EVICTION_POLICY, cachePreference.getEvictionCachePolicy().toString());

        return jsonObject;
    }

    public static GetCacheResponse buildGetCacheResponse(Collection<JSONObject> elements) {

        return GetCacheResponse.builder()
                .withData(GetCacheResponseData.builder()
                        .withAttributes(GetCacheResponseAttributes.builder()
                                .withPayload(GetCacheResponsePayload.builder()
                                        .withElements(elements)
                                        .build())
                                .build())
                        .withId(UUID.randomUUID().toString())
                        .withType(CACHE_RESOURCE_TYPE)
                        .build())
                .build();
    }

    public static GetCacheResponse buildGetCacheResponse(JSONObject elements) {

        return GetCacheResponse.builder()
                .withData(GetCacheResponseData.builder()
                        .withAttributes(GetCacheResponseAttributes.builder()
                                .withPayload(GetCacheResponsePayload.builder()
                                        .withElement(elements)
                                        .build())
                                .build())
                        .withId(UUID.randomUUID().toString())
                        .withType(CACHE_RESOURCE_TYPE)
                        .build())
                .build();
    }

    public static JSONObject ConvertToJsonElement(String key, String value) {
        return new JSONObject().put(key, value);
    }

}
