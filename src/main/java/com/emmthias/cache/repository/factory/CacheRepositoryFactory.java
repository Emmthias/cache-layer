package com.emmthias.cache.repository.factory;

import com.emmthias.cache.domain.CacheObject;
import com.emmthias.cache.domain.CachePreference;
import com.emmthias.cache.exception.AlreadyKeyFoundException;
import com.emmthias.cache.exception.KeyNotFoundException;
import com.emmthias.cache.repository.impl.ICacheRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.emmthias.cache.constants.Constants.ALREADY_BUCKET_NAME_IN_USE_MSG;
import static com.emmthias.cache.constants.Constants.DELETED_BUCKET_MESSAGE;
import static com.emmthias.cache.constants.Constants.KEY_NOT_FOUND_MSG;

@Repository
public class CacheRepositoryFactory implements ICacheRepositoryFactory {

    @Autowired
    ICacheAbstractRepositoryFactory factory;

    Map<String, ICacheRepository> cacheMap = new LinkedHashMap<>();

    /**
     * the map instance.
     *
     * @return the map instance.
     */
    @Override
    public Map<String, ICacheRepository> getBucketMap() {
        return this.cacheMap;
    }

    /**
     * Create bucket.
     *
     * @param key             the bucket identifier.
     * @param elements        the elements to be added.
     * @param cachePreference cache preferences.
     * @return the currentBucket.
     */
    @Override
    public Collection<JSONObject> createBucket(String key, List<CacheObject> elements,
                                               CachePreference cachePreference) {
        if (cacheMap.containsKey(key)) {
            throw new AlreadyKeyFoundException(String.format(ALREADY_BUCKET_NAME_IN_USE_MSG, key));
        }
        cacheMap.put(key, factory.getFactory(cachePreference));

        return cacheMap.get(key).addObjects(elements);

    }

    /**
     * Get bucket by key.
     *
     * @param key the bucket identifier.
     * @return the elements of a current bucket.
     */
    @Override
    public Collection<JSONObject> getBucket(String key) {
        if (!cacheMap.containsKey(key)) {
            throw new KeyNotFoundException(String.format(KEY_NOT_FOUND_MSG, key));
        }

        return cacheMap.get(key).getAllCacheObject();
    }

    /**
     * update an element into a bucket identified by key.
     *
     * @param key     the bucket identifier.
     * @param element the element that will replace the stored value.
     * @return the updated element.
     */
    @Override
    public JSONObject updateObjectByKey(String key, CacheObject element) {
        if (!cacheMap.containsKey(key)) {
            throw new KeyNotFoundException(String.format(KEY_NOT_FOUND_MSG, key));
        }
        return (JSONObject) cacheMap.get(key).updateCacheObject(element);
    }

    /**
     * patch an element into a bucket identified by key.
     *
     * @param key     the bucket identifier.
     * @param element the element to be patched following the merge approach.
     * @return the patched element.
     */
    @Override
    public JSONObject patchObjectKey(String key, CacheObject element) {
        if (!cacheMap.containsKey(key)) {
            throw new KeyNotFoundException(String.format(KEY_NOT_FOUND_MSG, key));
        }
        return (JSONObject) cacheMap.get(key).updateCacheObject(element);
    }

    /**
     * add a list of objects into a bucket identified by key.
     *
     * @param key      the bucket identifier.
     * @param elements the elements to be added.
     * @return the list of added elements.
     */
    @Override
    public Collection<JSONObject> addObjectByKey(String key, List<CacheObject> elements) {
        if (!cacheMap.containsKey(key)) {
            throw new KeyNotFoundException(String.format(KEY_NOT_FOUND_MSG, key));
        }
        return cacheMap.get(key).addObjects(elements);
    }

    /**
     * delete a complete bucket identified by key.
     *
     * @param key the bucket identifier.
     * @return the object deleted.
     */
    @Override
    public String deleteBucket(String key) {
        if (!cacheMap.containsKey(key)) {
            throw new KeyNotFoundException(String.format(KEY_NOT_FOUND_MSG, key));
        }
        
        cacheMap.remove(key, cacheMap.get(key));
        return String.format(DELETED_BUCKET_MESSAGE, key);
    }
}
