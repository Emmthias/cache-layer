package com.emmthias.cache.domain;

import java.util.Collection;

/**
 * ICache interface helper for operations
 *
 * @param <T> Class that will hold `data`
 * @param <V> type that will handle the key
 */
public interface ICache<T, V> {

    /**
     * Add object into a Key object
     *
     * @param key    the key that identify the object into the cache structure
     * @param Object the object to be inserted
     * @return the inserted object.
     */
    T addObject(V key, T... Object);

    /**
     * Retrieve the collection object associated to a key
     *
     * @param Key the key that identify the object into the cache structure
     * @return
     */
    Collection<T> getCacheObject(V Key);

    /**
     * Update a cache object
     *
     * @param Key the key that identify the object into the cache structure
     * @return the updated object
     */
    T updateCacheObject1(V Key, T... Object);

    /**
     * delete a cache object
     *
     * @param Key the key that identify the object into the cache structure
     * @return the deletion status
     */
    Boolean deleteCacheObject(V Key);
}
