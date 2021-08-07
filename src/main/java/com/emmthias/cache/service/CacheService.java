package com.emmthias.cache.service;

import com.emmthias.cache.domain.CacheObject;
import com.emmthias.cache.domain.CachePreference;
import com.emmthias.cache.exception.KeyNotFoundException;
import com.emmthias.cache.repository.factory.ICacheRepositoryFactory;
import com.emmthias.cache.repository.impl.ICacheRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class CacheService implements ICacheService {

    @Autowired
    ICacheRepositoryFactory factory;

    /**
     * Return all the buckets availables in the system.
     *
     * @return the map Instance.
     */
    @Override
    public CompletableFuture<Map<String, ICacheRepository>> getMapInstance() {
        return CompletableFuture.completedFuture(this.factory.getBucketMap());
    }

    /**
     * Create the cache Object
     *
     * @param key             the key identifier for this cache bucket
     * @param elements         The element that will be store into this bucket
     * @param cachePreference the configuration to be applied.
     * @return the created cache list
     */
    @Override
    public CompletableFuture<Collection<JSONObject>> createCacheObject(String key, List<CacheObject> elements,
                                                                       CachePreference cachePreference) {
        return CompletableFuture.completedFuture(factory.createBucket(key, elements, cachePreference));
    }

    @Override
    public CompletableFuture<Collection<JSONObject>> getCacheObjectByKey(String key) {
        return CompletableFuture.completedFuture(factory.getBucket(key));
    }

    /**
     * Delete cache bucket
     *
     * @param key The key identifier for this cache bucket
     * @return confirmation message or KeyNotFoundException
     * @throws KeyNotFoundException if the key is not present in the cache bucket
     */
    @Override
    public CompletableFuture<String> delete(String key) throws KeyNotFoundException {
        return CompletableFuture.completedFuture(factory.deleteBucket(key));
    }

    /**
     * Create cache Bucket
     *
     * @param key     The key identifier for this cache bucket
     * @param elements The element that will be store into this bucket
     * @return The created cache list
     */
    @Override
    public CompletableFuture<Collection<JSONObject>> addCacheObject(String key, List<CacheObject> elements) {
        return CompletableFuture.completedFuture(factory.addObjectByKey(key, elements));
    }

    /**
     * update an object
     *
     * @param key     The key identifier for this cache bucket
     * @param element The element that will replace the value associated with the key.
     * @return the updated object
     */
    @Override
    public CompletableFuture<JSONObject> updateObject(String key, CacheObject element) {
        return CompletableFuture.completedFuture(factory.updateObjectByKey(key, element));
    }

    /**
     * patch an object
     *
     * @param key     The key identifier for this cache bucket
     * @param element The element that will replace the value associated with the key.
     * @return the patched object.
     */
    @Override
    public CompletableFuture<JSONObject> patchObject(String key, CacheObject element) {
        return CompletableFuture.completedFuture(factory.patchObjectKey(key, element));
    }

    /**
     * delete an object
     *
     * @param key     The key identifier for this cache bucket
     * @param element The element that will remove the value associated with the key.
     * @return the patched object.
     */
    @Override
    public CompletableFuture<String> deleteObject(String key, CacheObject element) {
        return CompletableFuture.completedFuture(factory.deleteObjectKey(key, element));
    }
}
