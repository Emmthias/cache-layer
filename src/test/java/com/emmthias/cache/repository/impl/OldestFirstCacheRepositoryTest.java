package com.emmthias.cache.repository.impl;

import com.emmthias.cache.domain.CacheObject;
import com.emmthias.cache.domain.CachePreference;
import com.emmthias.cache.exception.KeyNotFoundException;
import com.emmthias.cache.utils.Constant;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class OldestFirstCacheRepositoryTest {
    @InjectMocks
    OldestFirstCacheRepository repository;

    @Test
    public void whenPatchCacheObject_shouldReturnObject() {
        ReflectionTestUtils.invokeSetterMethod(repository, "setCachePreference",
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        CachePreference preference = repository.getCachePreference();
        assertNotNull(preference);
        JSONObject object = new JSONObject();
        object.put("id", "1");
        Collection<JSONObject> response = repository.addObjects(List.of(new CacheObject(object)));
        JSONObject responsePatchObject = repository.patchCacheObject(new CacheObject(object));
        assertNotNull(responsePatchObject);
    }

    @Test
    public void whenGetSingleCacheObject_shouldReturnObject() {
        ReflectionTestUtils.invokeSetterMethod(repository, "setCachePreference",
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        CachePreference preference = repository.getCachePreference();
        JSONObject object = new JSONObject();
        object.put("id", "1");
        repository.addObjects(List.of(new CacheObject(object)));
        JSONObject response = repository.getSingleCacheObject(new CacheObject(object).getKey());
        assertNotNull(response);
        assertEquals(object, response);
    }

    @Test
    public void whenGetAllCacheObject_shouldReturnObject() {
        ReflectionTestUtils.invokeSetterMethod(repository, "setCachePreference",
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        CachePreference preference = repository.getCachePreference();
        JSONObject object = new JSONObject();
        object.put("id", "1");
        repository.addObjects(List.of(new CacheObject(object)));
        Collection<JSONObject> response = repository.getAllCacheObject();
        assertNotNull(response);
        assertEquals(object, response.iterator().next());
    }

    @Test
    public void whenUpdateCacheObject_shouldReturnObject() {
        ReflectionTestUtils.invokeSetterMethod(repository, "setCachePreference",
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        CachePreference preference = repository.getCachePreference();
        JSONObject object = new JSONObject();
        object.put("id", "1");
        repository.addObjects(List.of(new CacheObject(object)));
        JSONObject response = repository.updateCacheObject(new CacheObject(object));
        assertNotNull(response);
        assertEquals(object, response);
    }

    @Test
    public void whenUpdateCacheObject__keyNotFound_ThrowKeyNotFoundException() {
        ReflectionTestUtils.invokeSetterMethod(repository, "setCachePreference",
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        CachePreference preference = repository.getCachePreference();
        JSONObject object = new JSONObject();
        object.put("id", "1");

        String expectedMessage = "Provided key '3370' was no found";
        Exception exception = assertThrows(KeyNotFoundException.class, () -> {
            repository.updateCacheObject(new CacheObject(object));
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void whenDeleteCacheObject_shouldReturnObject() {
        ReflectionTestUtils.invokeSetterMethod(repository, "setCachePreference",
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        CachePreference preference = repository.getCachePreference();
        JSONObject object = new JSONObject();
        object.put("id", "1");
        repository.addObjects(List.of(new CacheObject(object)));
        Boolean response = repository.deleteCacheObject(new CacheObject(object));
        assertTrue(response);
    }


    @Test
    public void whenDeleteCacheObject_keyNotFound_ThrowKeyNotFoundException() {
        ReflectionTestUtils.invokeSetterMethod(repository, "setCachePreference",
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        CachePreference preference = repository.getCachePreference();
        JSONObject object = new JSONObject();
        object.put("id", "1");
        String expectedMessage = "Provided key '3370' was no found";
        Exception exception = assertThrows(KeyNotFoundException.class, () -> {
            repository.deleteCacheObject(new CacheObject(object));
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenScheduleCleanElements_shouldClean() {
        ReflectionTestUtils.invokeSetterMethod(repository, "setCachePreference",
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        CachePreference preference = repository.getCachePreference();
        repository.scheduleCleanElements(1);
    }
}