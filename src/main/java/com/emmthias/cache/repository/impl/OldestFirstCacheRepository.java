package com.emmthias.cache.repository.impl;

import com.emmthias.cache.domain.CacheObject;
import com.emmthias.cache.domain.CachePreference;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.emmthias.cache.constants.Constants.ELDEST_MECHANISM_APPLIED;
import static com.emmthias.cache.converter.Converters.mergeObjects;

@Component
@Scope(value = "prototype")
public class OldestFirstCacheRepository implements ICacheRepository<String, CacheObject, JSONObject> {

    private final Logger logger = LoggerFactory.getLogger(OldestFirstCacheRepository.class);

    // override `removeEldestEntry` method to use the build-in functionality
    Map<String, JSONObject> data = new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, JSONObject> eldest) {
            logger.warn(ELDEST_MECHANISM_APPLIED);
            return this.size() > getCachePreference().getSlotNumber();
        }
    };

    private CachePreference cachePreference;

    /**
     * set cache preference associated with {@link CachePreference}
     *
     * @param cachePreference
     */
    @Override
    @Autowired
    public void setCachePreference(CachePreference cachePreference) {
        this.cachePreference = cachePreference;
        scheduleCleanElements(cachePreference.getTimeToLive());
    }

    /**
     * get cache preference associated with {@link CachePreference}
     *
     * @return
     */
    @Override
    public CachePreference getCachePreference() {
        return cachePreference;
    }

    /**
     * get the current map instance
     *
     * @return the map instance.
     */
    @Override
    public Map<String, JSONObject> getInstance() {
        return this.data;
    }

    /**
     * Patch a cache object by merge approach
     *
     * @param object the object value
     * @return the updated object
     */
    @Override
    public JSONObject patchCacheObject(CacheObject object) {
        checkMapExistence(object.getKey());

        JSONObject mergeObject = mergeObjects(this.data.get(object.getKey()), object);
        return this.data.replace(object.getKey(), mergeObject);
    }
}
