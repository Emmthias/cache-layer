package com.emmthias.cache.service;

import com.emmthias.cache.domain.CacheObject;
import com.emmthias.cache.domain.CachePreference;
import com.emmthias.cache.exception.KeyNotFoundException;
import com.emmthias.cache.repository.impl.ICacheRepository;
import org.json.JSONObject;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface ICacheService {

    /**
     * Return all the buckets availables in the system.
     * @return the map Instance.
     */
    CompletableFuture<Map<String, ICacheRepository>> getMapInstance();

    /**
     * Create cache Bucket
     *
     * @param key             The key identifier for this cache bucket
     * @param element         The element that will be store into this bucket
     * @param cachePreference The configuration to be applied.
     * @return The created cache list
     */
    CompletableFuture<Collection<JSONObject>> createCacheObject(String key, List<CacheObject> element,
                                                                CachePreference cachePreference);

    /**
     * Get cache Bucket values by key
     *
     * @param key The key identifier for this cache bucket
     * @return all the element store in the cache bucket.
     */
    CompletableFuture<Collection<JSONObject>> getCacheObjectByKey(String key);

    /**
     * Delete cache bucket
     *
     * @param key The key identifier for this cache bucket
     * @return confirmation message or KeyNotFoundException
     * @throws KeyNotFoundException if the key is not present in the cache bucket
     */
    CompletableFuture<String> delete(String key) throws KeyNotFoundException;

    /**
     * Create cache Bucket
     *
     * @param key     The key identifier for this cache bucket
     * @param element The element that will be store into this bucket
     * @return The created cache list
     */
    CompletableFuture<Collection<JSONObject>> addCacheObject(String key, List<CacheObject> element);

    /**
     * update an object
     *
     * @param key     The key identifier for this cache bucket
     * @param element The element that will replace the value associated with the key.
     * @return the updated object
     */
    CompletableFuture<JSONObject> updateObject(String key, CacheObject element);

    /**
     * patch an object
     *
     * @param key     The key identifier for this cache bucket
     * @param element The element that will replace the value associated with the key.
     * @return the patched object.
     */
    CompletableFuture<JSONObject> patchObject(String key, CacheObject element);

    /**
     * delete an object
     *
     * @param key     The key identifier for this cache bucket
     * @param element The element that will remove the value associated with the key.
     * @return the patched object.
     */
    CompletableFuture<String> deleteObject(String key, CacheObject element);
}
