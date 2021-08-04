package com.emmthias.cache.repository.impl;

import com.emmthias.cache.domain.CacheObject;
import com.emmthias.cache.domain.CachePreference;
import com.emmthias.cache.validator.NewestCacheValidator;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.emmthias.cache.constants.Constants.NEWEST_POLICY_WARN_MESSAGE;
import static com.emmthias.cache.converter.Converters.mergeObjects;

@Component
@Scope(value = "prototype")
public class NewestFirstCacheRepository implements ICacheRepository<String, CacheObject, JSONObject> {

    private final Logger logger = LoggerFactory.getLogger(NewestFirstCacheRepository.class);

    Map<String, JSONObject> data = new LinkedHashMap<>();

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
     * Add object into a Key object
     *
     * @param objects the object to be inserted
     * @return the inserted object.
     */
    @Override
    public Collection<JSONObject> addObjects(List<CacheObject> objects) {

        if (this.data.size() + objects.size() > getCachePreference().getSlotNumber()) {

            //extra elements
            int missingElementNumbers =  this.data.size() + objects.size() - getCachePreference().getSlotNumber();

            List<String> keys = new ArrayList<String>(this.data.keySet());

            List<String> keysToBeRemoved = keys.subList(keys.size() - missingElementNumbers, keys.size());

            keys = null; // clean unused elements

            NewestCacheValidator.INSTANCE.applyEvictionPolicy(getInstance(), keysToBeRemoved);

            logger.warn(String.format(NEWEST_POLICY_WARN_MESSAGE, missingElementNumbers));
        }

        return ICacheRepository.super.addObjects(objects);
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
