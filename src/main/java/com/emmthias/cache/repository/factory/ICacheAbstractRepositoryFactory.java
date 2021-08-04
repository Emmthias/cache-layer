package com.emmthias.cache.repository.factory;

import com.emmthias.cache.domain.CachePreference;
import com.emmthias.cache.domain.EvictionCachePolicy;
import com.emmthias.cache.repository.impl.ICacheRepository;

public interface ICacheAbstractRepositoryFactory<T extends ICacheRepository> {
    /**
     * create an instance using the appropriate factory
     * @param cachePreference the cache preference.
     * @return the repository  following the preferences.
     */
    T getFactory(CachePreference cachePreference);
}
