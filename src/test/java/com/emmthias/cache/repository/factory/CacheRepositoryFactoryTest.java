package com.emmthias.cache.repository.factory;

import com.emmthias.cache.domain.CacheObject;
import com.emmthias.cache.exception.AlreadyKeyFoundException;
import com.emmthias.cache.exception.KeyNotFoundException;
import com.emmthias.cache.repository.impl.RejectCacheRepository;
import com.emmthias.cache.utils.Constant;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CacheRepositoryFactoryTest {

    @InjectMocks
    CacheRepositoryFactory cacheRepositoryFactory;

    @Mock
    FactoryProvider factoryProvider;

    @Test
    public void whenGetBucketMap_returnMap() {
        assertNotNull(cacheRepositoryFactory.getBucketMap());
    }

    @Test
    public void whenCreateBucket_returnCollection() {
        RejectCacheRepository repository = new RejectCacheRepository();
        repository.setCachePreference(Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        Mockito.when(factoryProvider.getFactory(any())).thenReturn(repository);
        JSONObject currentObject = new JSONObject().put("id", "1");
        Collection<JSONObject> my_bucket_key = cacheRepositoryFactory.createBucket("test",
                Arrays.asList(new CacheObject(currentObject)),
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());

        assertNotNull(my_bucket_key);
        assertEquals(currentObject, my_bucket_key.iterator().next());
    }

    @Test
    public void whenCreateBucket_withRepeatedKey_throwAlreadyKeyFoundException() {
        RejectCacheRepository repository = new RejectCacheRepository();
        repository.setCachePreference(Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        Mockito.when(factoryProvider.getFactory(any())).thenReturn(repository);
        JSONObject currentObject = new JSONObject().put("id", "1");
        Collection<JSONObject> my_bucket_key = cacheRepositoryFactory.createBucket("test",
                Arrays.asList(new CacheObject(currentObject)),
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());

        assertNotNull(my_bucket_key);
        assertEquals(currentObject, my_bucket_key.iterator().next());

        String expectedMessage = "Provided key 'test' was already in use";
        Exception exception = assertThrows(AlreadyKeyFoundException.class, () -> {
            cacheRepositoryFactory.createBucket("test",
                    Arrays.asList(new CacheObject(currentObject)),
                    Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenGetBucket_returnCollection() {
        RejectCacheRepository repository = new RejectCacheRepository();
        repository.setCachePreference(Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        Mockito.when(factoryProvider.getFactory(any())).thenReturn(repository);
        JSONObject currentObject = new JSONObject().put("id", "1");
        Collection<JSONObject> my_bucket_key = cacheRepositoryFactory.createBucket("test",
                Arrays.asList(new CacheObject(currentObject)),
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());

        Collection<JSONObject> result = cacheRepositoryFactory.getBucket("test");

        assertNotNull(my_bucket_key);
        assertEquals(1, my_bucket_key.size());
    }

    @Test
    public void whenGetBucket_withoutKey_shouldThrowKeyNotFoundException() {
        String expectedMessage = "Provided key 'test' was no found";
        Exception exception = assertThrows(KeyNotFoundException.class, () -> {
            cacheRepositoryFactory.getBucket("test");
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenUpdateObjectByKey_withoutKey_shouldThrowKeyNotFoundException() {
        String expectedMessage = "Provided key 'test' was no found";
        Exception exception = assertThrows(KeyNotFoundException.class, () -> {
            cacheRepositoryFactory.updateObjectByKey("test", new CacheObject(new JSONObject()));
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenUpdateObjectByKey_Return_JsonObject() {
        RejectCacheRepository repository = new RejectCacheRepository();
        repository.setCachePreference(Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        Mockito.when(factoryProvider.getFactory(any())).thenReturn(repository);
        JSONObject currentObject = new JSONObject().put("id", "1");
        Collection<JSONObject> my_bucket_key = cacheRepositoryFactory.createBucket("test",
                Arrays.asList(new CacheObject(currentObject)),
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());

        Collection<JSONObject> result = cacheRepositoryFactory.getBucket("test");
        JSONObject updateObjectByKey = cacheRepositoryFactory.updateObjectByKey("test", new CacheObject(currentObject));
        assertEquals(currentObject, updateObjectByKey);
    }


    @Test
    public void whenPatchObjectKey_withoutKey_shouldThrowKeyNotFoundException() {
        String expectedMessage = "Provided key 'test' was no found";
        Exception exception = assertThrows(KeyNotFoundException.class, () -> {
            cacheRepositoryFactory.patchObjectKey("test", new CacheObject(new JSONObject()));
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenPatchObjectKey_Return_JsonObject() {
        RejectCacheRepository repository = new RejectCacheRepository();
        repository.setCachePreference(Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        Mockito.when(factoryProvider.getFactory(any())).thenReturn(repository);
        JSONObject currentObject = new JSONObject().put("id", "1");
        Collection<JSONObject> my_bucket_key = cacheRepositoryFactory.createBucket("test",
                Arrays.asList(new CacheObject(currentObject)),
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());

        Collection<JSONObject> result = cacheRepositoryFactory.getBucket("test");
        JSONObject updateObjectByKey = cacheRepositoryFactory.patchObjectKey("test", new CacheObject(currentObject));
        assertEquals(currentObject, updateObjectByKey);
    }

    @Test
    public void whenDeleteObjectKey_withoutKey_shouldThrowKeyNotFoundException() {
        String expectedMessage = "Provided key 'test' was no found";
        Exception exception = assertThrows(KeyNotFoundException.class, () -> {
            cacheRepositoryFactory.deleteObjectKey("test", new CacheObject(new JSONObject()));
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenDeleteObjectKey_Return_JsonObject() {
        RejectCacheRepository repository = new RejectCacheRepository();
        repository.setCachePreference(Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        Mockito.when(factoryProvider.getFactory(any())).thenReturn(repository);
        JSONObject currentObject = new JSONObject().put("id", "1");
        Collection<JSONObject> my_bucket_key = cacheRepositoryFactory.createBucket("test",
                Arrays.asList(new CacheObject(currentObject)),
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());

        Collection<JSONObject> result = cacheRepositoryFactory.getBucket("test");
        String response = cacheRepositoryFactory.deleteObjectKey("test", new CacheObject(currentObject));
        assertEquals("The object '3370' was deleted successfully", response);
    }

    @Test
    public void whenAddObjectByKey_withoutKey_shouldThrowKeyNotFoundException() {
        String expectedMessage = "Provided key 'test' was no found";
        Exception exception = assertThrows(KeyNotFoundException.class, () -> {
            cacheRepositoryFactory.addObjectByKey("test", Arrays.asList(new CacheObject(new JSONObject())));
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenAddObjectByKey_Return_JsonObject() {
        RejectCacheRepository repository = new RejectCacheRepository();
        repository.setCachePreference(Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        Mockito.when(factoryProvider.getFactory(any())).thenReturn(repository);
        JSONObject currentObject = new JSONObject().put("id", "1");
        JSONObject CreatedObject = new JSONObject().put("name", "2");
        Collection<JSONObject> my_bucket_key = cacheRepositoryFactory.createBucket("test",
                Arrays.asList(new CacheObject(currentObject)),
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());

        cacheRepositoryFactory.getBucket("test");
        Collection<JSONObject> result = cacheRepositoryFactory.addObjectByKey("test",
                Arrays.asList(new CacheObject(CreatedObject)));

        assertNotNull(result);
        assertEquals(CreatedObject, result.iterator().next());
    }

    @Test
    public void whenDeleteBucket_withoutKey_shouldThrowKeyNotFoundException() {
        String expectedMessage = "Provided key 'test' was no found";
        Exception exception = assertThrows(KeyNotFoundException.class, () -> {
            cacheRepositoryFactory.deleteBucket("test");
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenDeleteBucket_Return_JsonObject() {
        RejectCacheRepository repository = new RejectCacheRepository();
        repository.setCachePreference(Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        Mockito.when(factoryProvider.getFactory(any())).thenReturn(repository);
        JSONObject currentObject = new JSONObject().put("id", "1");
        Collection<JSONObject> my_bucket_key = cacheRepositoryFactory.createBucket("test",
                Arrays.asList(new CacheObject(currentObject)),
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());

        Collection<JSONObject> result = cacheRepositoryFactory.getBucket("test");
        String response = cacheRepositoryFactory.deleteBucket("test");
        assertEquals("The bucket 'test' was deleted successfully", response);
    }


}