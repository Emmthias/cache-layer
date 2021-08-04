package com.emmthias.cache.repository.factory;

import com.emmthias.cache.domain.CachePreference;
import com.emmthias.cache.repository.impl.ICacheRepository;
import com.emmthias.cache.repository.impl.NewestFirstCacheRepository;
import com.emmthias.cache.repository.impl.OldestFirstCacheRepository;
import com.emmthias.cache.repository.impl.RejectCacheRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FactoryProvider implements ICacheAbstractRepositoryFactory<ICacheRepository> {

    /**
     * create an instance using the appropriate factory
     *
     * @param cachePreference the cache preference.
     * @return the repository  following the preferences.
     */
    @Override
    public ICacheRepository getFactory(CachePreference cachePreference) {

        ICacheRepository currentRepository;

        switch (cachePreference.getEvictionCachePolicy()) {
            case OLDER_FIRST:
                currentRepository = new OldestFirstCacheRepository();
                break;
            case NEWEST_FIRST:
                currentRepository = new NewestFirstCacheRepository();
                break;
            default:
                currentRepository = new RejectCacheRepository();
        }

        currentRepository.setCachePreference(cachePreference);

        return currentRepository;
    }
}
