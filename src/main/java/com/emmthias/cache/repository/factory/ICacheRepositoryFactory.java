package com.emmthias.cache.repository.factory;

import com.emmthias.cache.domain.CacheObject;
import com.emmthias.cache.domain.CachePreference;
import com.emmthias.cache.repository.impl.ICacheRepository;
import org.json.JSONObject;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ICacheRepositoryFactory {

    /**
     * the map instance.
     *
     * @return the map instance.
     */
    Map<String, ICacheRepository> getBucketMap();

    /**
     * Create bucket.
     *
     * @param key             the bucket identifier.
     * @param elements        the elements to be added.
     * @param cachePreference cache preferences.
     * @return the currentBucket.
     */
    Collection<JSONObject> createBucket(String key, List<CacheObject> elements, CachePreference cachePreference);

    /**
     * Get bucket by key.
     *
     * @param key the bucket identifier.
     * @return the elements of a current bucket.
     */
    Collection<JSONObject> getBucket(String key);

    /**
     * update an element into a bucket identified by key.
     *
     * @param key     the bucket identifier.
     * @param element the element that will replace the stored value.
     * @return the updated element.
     */
    JSONObject updateObjectByKey(String key, CacheObject element);

    /**
     * patch an element into a bucket identified by key.
     *
     * @param key     the bucket identifier.
     * @param element the element to be patched following the merge approach.
     * @return the patched element.
     */
    JSONObject patchObjectKey(String key, CacheObject element);

    /**
     * delete an element into a bucket identified by key.
     *
     * @param key     the bucket identifier.
     * @param element the element to be patched following the merge approach.
     * @return the patched element.
     */
    String deleteObjectKey(String key, CacheObject element);

    /**
     * add a list of objects into a bucket identified by key.
     *
     * @param key      the bucket identifier.
     * @param elements the elements to be added.
     * @return the list of added elements.
     */
    Collection<JSONObject> addObjectByKey(String key, List<CacheObject> elements);

    /**
     * delete a complete bucket identified by key.
     *
     * @param key the bucket identifier.
     * @return the confirmation message
     */
    String deleteBucket(String key);

}
