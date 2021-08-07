package com.emmthias.cache.repository.factory;

import com.emmthias.cache.domain.CachePreference;
import com.emmthias.cache.domain.EvictionCachePolicy;
import com.emmthias.cache.repository.impl.ICacheRepository;
import com.emmthias.cache.repository.impl.NewestFirstCacheRepository;
import com.emmthias.cache.repository.impl.OldestFirstCacheRepository;
import com.emmthias.cache.repository.impl.RejectCacheRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class FactoryProviderTest {

    @InjectMocks
    FactoryProvider factoryProvider;

    @Test
    public void whenGetFactory_returnDefaultRepository() {
        CachePreference cachePreference =
                CachePreference.builder().withEvictionCachePolicy(EvictionCachePolicy.REJECT.name()).withTimeToLive(0).build();
        ICacheRepository factory = factoryProvider.getFactory(cachePreference);
        assertNotNull(factory);
        assertTrue(factory instanceof RejectCacheRepository);
    }

    @Test
    public void whenGetFactory_returnNewestFirstRepository() {
        CachePreference cachePreference =
                CachePreference.builder().withEvictionCachePolicy(EvictionCachePolicy.NEWEST_FIRST.name()).withTimeToLive(0).build();
        ICacheRepository factory = factoryProvider.getFactory(cachePreference);
        assertNotNull(factory);
        assertTrue(factory instanceof NewestFirstCacheRepository);
    }

    @Test
    public void whenGetFactory_returnOlderFirstRepository() {
        CachePreference cachePreference =
                CachePreference.builder().withEvictionCachePolicy(EvictionCachePolicy.OLDER_FIRST.name()).withTimeToLive(0).build();
        ICacheRepository factory = factoryProvider.getFactory(cachePreference);
        assertNotNull(factory);
        assertTrue(factory instanceof OldestFirstCacheRepository);
    }
}