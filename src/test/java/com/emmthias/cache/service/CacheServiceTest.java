package com.emmthias.cache.service;

import com.emmthias.cache.domain.CacheObject;
import com.emmthias.cache.exception.KeyNotFoundException;
import com.emmthias.cache.repository.factory.CacheRepositoryFactory;
import com.emmthias.cache.repository.impl.ICacheRepository;
import com.emmthias.cache.repository.impl.RejectCacheRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.emmthias.cache.utils.Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CacheServiceTest {

    @InjectMocks
    CacheService cacheService;

    @Mock
    CacheRepositoryFactory repositoryFactory;

    @Test
    void whenGetMapInstance_withNoRecords_thenReturnEmptyBucketMap() throws ExecutionException, InterruptedException {
        Map<String, ICacheRepository> currentMap = new HashMap<>();
        Mockito.when(repositoryFactory.getBucketMap()).thenReturn(currentMap);
        Map<String, ICacheRepository> cache = cacheService.getMapInstance().get();
        assertTrue(cache.isEmpty());

    }

    @Test
    void whenGetMapInstance_withRecords_thenReturnBucketMap() throws ExecutionException, InterruptedException {
        Map<String, ICacheRepository> currentMap = new HashMap<>();
        currentMap.put("My test Cache", new RejectCacheRepository());
        Mockito.when(repositoryFactory.getBucketMap()).thenReturn(currentMap);
        Map<String, ICacheRepository> cache = cacheService.getMapInstance().get();
        assertEquals(1, cache.entrySet().size());

    }

    @Test
    void wheCreateCacheObject_thenReturnBucketMap() throws ExecutionException, InterruptedException {

        Collection<JSONObject> response = new ArrayList<>();
        JSONObject currentObject = new JSONObject().put("id", "1");
        response.add(currentObject);

        Mockito.when(repositoryFactory.createBucket(any(), any(), any())).thenReturn(response);

        Collection<JSONObject> my_bucket_key = cacheService.createCacheObject("My Bucket Key",
                Arrays.asList(new CacheObject(currentObject)),
                getEXPECTED_DEFAULT_CACHE_PREFERENCE()).get();

        assertNotNull(my_bucket_key);
        assertEquals(currentObject, my_bucket_key.iterator().next());
    }

    @Test
    void whenGetCacheObjectByKey_thenReturnBucketMap() throws ExecutionException, InterruptedException {

        Collection<JSONObject> response = new ArrayList<>();
        JSONObject currentObject = new JSONObject().put("id", "1");
        response.add(currentObject);

        Mockito.when(repositoryFactory.getBucket(any())).thenReturn(response);

        Collection<JSONObject> my_bucket_key = cacheService.getCacheObjectByKey("My Bucket Key").get();

        assertNotNull(my_bucket_key);
        assertEquals(currentObject, my_bucket_key.iterator().next());
    }

    @Test
    void whenDelete_thenReturnBucketMap() throws ExecutionException, InterruptedException {

        String expectedResponse = "deleted";
        Mockito.when(repositoryFactory.deleteBucket(any())).thenReturn(expectedResponse);

        String currentResponse = cacheService.delete("My Bucket Key").get();

        assertNotNull(currentResponse);
        assertEquals(expectedResponse, currentResponse);
    }

    @Test
    void whenDelete_givenNotValidKey_thenThrowKeyNotFoundException() throws InterruptedException {
        String expectedMessage = "key not found";

        Mockito.when(repositoryFactory.deleteBucket(any())).thenThrow(new KeyNotFoundException(expectedMessage));

        Exception exception = assertThrows(KeyNotFoundException.class, () -> {
            String currentResponse = cacheService.delete("My Bucket Key").get();
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenAddCacheObject_thenReturnBucketMap() throws ExecutionException, InterruptedException {

        Collection<JSONObject> response = new ArrayList<>();
        JSONObject currentObject = new JSONObject().put("id", "1");
        response.add(currentObject);

        Mockito.when(repositoryFactory.addObjectByKey(any(), any())).thenReturn(response);

        Collection<JSONObject> my_bucket_key =
                cacheService.addCacheObject("My Bucket Key", Arrays.asList(new CacheObject(currentObject))).get();

        assertNotNull(my_bucket_key);
        assertEquals(currentObject, my_bucket_key.iterator().next());
    }

    @Test
    void whenUpdateObject_thenReturnBucketMap() throws ExecutionException, InterruptedException {

        JSONObject currentObject = new JSONObject().put("id", "1");

        Mockito.when(repositoryFactory.updateObjectByKey(any(), any())).thenReturn(currentObject);

        JSONObject my_bucket_key = cacheService.updateObject("My Bucket Key", new CacheObject(currentObject)).get();

        assertNotNull(my_bucket_key);
        assertEquals(currentObject, my_bucket_key);
    }


    @Test
    void whenPatchObject_thenReturnBucketMap() throws ExecutionException, InterruptedException {

        JSONObject currentObject = new JSONObject().put("id", "1");

        Mockito.when(repositoryFactory.patchObjectKey(any(), any())).thenReturn(currentObject);

        JSONObject my_bucket_key = cacheService.patchObject("My Bucket Key", new CacheObject(currentObject)).get();

        assertNotNull(my_bucket_key);
        assertEquals(currentObject, my_bucket_key);
    }

    @Test
    void whenDeleteObject_thenReturnBucketMap() throws ExecutionException, InterruptedException {

        String expectedResponse = "deleted";
        Mockito.when(repositoryFactory.deleteObjectKey(any(), any())).thenReturn(expectedResponse);

        String currentResponse = cacheService.deleteObject("My Bucket Key", new CacheObject(new JSONObject())).get();

        assertNotNull(currentResponse);
        assertEquals(expectedResponse, currentResponse);
    }
}