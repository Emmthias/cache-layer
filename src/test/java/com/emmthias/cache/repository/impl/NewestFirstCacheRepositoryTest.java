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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class NewestFirstCacheRepositoryTest {

    @InjectMocks
    NewestFirstCacheRepository repository;

    @Test
    public void whenAddObjects_returnCollection() {
        ReflectionTestUtils.invokeSetterMethod(repository, "setCachePreference",
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        CachePreference preference = repository.getCachePreference();
        assertNotNull(preference);
        List<CacheObject> objects = new ArrayList<>();
        JSONObject object = new JSONObject();
        object.put("id", "1");
        objects.add(new CacheObject(object));
        Collection<JSONObject> response = repository.addObjects(objects);
        assertNotNull(response);
        assertEquals(1, response.size());
    }

    @Test
    public void whenCheckMapExistence_notFoundObject_shouldThrowException() {
        ReflectionTestUtils.invokeSetterMethod(repository, "setCachePreference",
                Constant.getEXPECTED_DEFAULT_CACHE_PREFERENCE());
        CachePreference preference = repository.getCachePreference();
        assertNotNull(preference);
        String expectedMessage = "Provided key 'key' was no found";
        Exception exception = assertThrows(KeyNotFoundException.class, () -> {
            repository.checkMapExistence("key");
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

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
}