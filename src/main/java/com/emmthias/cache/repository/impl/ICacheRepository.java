package com.emmthias.cache.repository.impl;

import com.emmthias.cache.domain.CachePreference;
import com.emmthias.cache.domain.ICacheObject;
import com.emmthias.cache.exception.KeyNotFoundException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.emmthias.cache.constants.Constants.AVOID_DELAY_EXECUTION;
import static com.emmthias.cache.constants.Constants.KEY_NOT_FOUND_MSG;
import static com.emmthias.cache.constants.Constants.SCHEDULER_CLEAN_LOG_MESSAGE;
import static com.emmthias.cache.constants.Constants.SCHEDULER_CONFIGURE_LOG_MESSAGE;
import static com.emmthias.cache.constants.Constants.SCHEDULER_NOT_CONFIGURE_LOG_MESSAGE;
import static java.util.Objects.nonNull;

/**
 * ICacheRepository interface helper for operations
 *
 * @param <I> Input parameter class
 * @param <K> type that will handle the key
 * @param <R> Class that will hold `data`
 */

public interface ICacheRepository<K, I extends ICacheObject<K, R>, R extends JSONObject> {
    Logger logger = LoggerFactory.getLogger(ICacheRepository.class);

    /**
     * get cache preference associated with {@link com.emmthias.cache.domain.CachePreference}
     *
     * @return
     */
    CachePreference getCachePreference();

    /**
     * set cache preference associated with {@link com.emmthias.cache.domain.CachePreference}
     *
     * @param cachePreference
     */
    void setCachePreference(CachePreference cachePreference);

    /**
     * Add object into a Key object
     *
     * @param objects the object to be inserted
     * @return the inserted object.
     */
    default Collection<R> addObjects(List<I> objects) {
        List<R> insertedElements = new ArrayList<>();

        objects.stream().forEach(e -> {
            getInstance().put(e.getKey(), e.getValue());
            insertedElements.add(getInstance().get(e.getKey()));
        });
        return insertedElements;
    }

    /**
     * get the current map instance
     *
     * @return the map instance.
     */
    Map<K, R> getInstance();

    /**
     * Retrieve the collection object associated to a key
     *
     * @param key the key that identify the object into the cache structure
     * @return
     */
    default R getSingleCacheObject(K key) {
        return getInstance().get(key);
    }

    /**
     * Retrieve the collection object list
     *
     * @return the current instance
     */
    default Collection<R> getAllCacheObject() {
        return getInstance().values();
    }

    /**
     * Update a cache object
     *
     * @param object the object value
     * @return the updated object
     */
    default R updateCacheObject(I object) {
        if (!getInstance().containsKey(object.getKey())) {
            throw new KeyNotFoundException(String.format(KEY_NOT_FOUND_MSG, object.getKey()));
        }
        getInstance().put(object.getKey(), object.getValue());
        return getInstance().get(object.getKey());
    }

    /**
     * Patch a cache object by merge approach
     *
     * @param object the object value
     * @return the updated object
     */
    R patchCacheObject(I object);

    /**
     * delete a cache object
     *
     * @param object the key that identify the object into the cache structure
     * @return the deletion status
     */
    default Boolean deleteCacheObject(I object) {
        if (!getInstance().containsKey(object.getKey())) {
            throw new KeyNotFoundException(String.format(KEY_NOT_FOUND_MSG, object.getKey()));
        }
        getInstance().remove(object.getKey(), getInstance().get(object.getKey()));
        return true;
    }

    /**
     * Helper method to check if the key provided is already in placed if not throws a KeyNotFound Exception.
     *
     * @param key the key that identify the object into the cache structure.
     */
    default void checkMapExistence(String key) {
        if (!getInstance().containsKey(key)) {
            throw new KeyNotFoundException(String.format(KEY_NOT_FOUND_MSG, key));
        }
    }

    default void scheduleCleanElements(int delayTime) {
        if (delayTime != AVOID_DELAY_EXECUTION) {
            logger.info(String.format(SCHEDULER_CONFIGURE_LOG_MESSAGE, delayTime));
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            Runnable task = () -> {

                logger.info((String.format(SCHEDULER_CLEAN_LOG_MESSAGE, getInstance().size())));
                getInstance().clear();
            };

            executor.scheduleAtFixedRate(task, delayTime, delayTime, TimeUnit.SECONDS);
        } else {
            logger.info(SCHEDULER_NOT_CONFIGURE_LOG_MESSAGE);
        }
    }
}
