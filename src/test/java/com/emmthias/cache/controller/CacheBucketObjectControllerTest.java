package com.emmthias.cache.controller;

import com.emmthias.cache.common.response.error.impl.CommonApiErrorResponse;
import com.emmthias.cache.common.response.success.impl.GetCacheResponse;
import com.emmthias.cache.exception.KeyNotFoundException;
import com.emmthias.cache.exception.RejectPolicyException;
import com.emmthias.cache.service.CacheService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static com.emmthias.cache.constants.Constants.CACHE_RESOURCE_TYPE;
import static com.emmthias.cache.constants.Constants.REJECT_POLICY_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CacheBucketObjectController.class)
class CacheBucketObjectControllerTest {

    private static final String URL = "/cache-service/api/bucket/1/object";
    @MockBean
    CacheService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetEndpointHasReach_returnSuccess() throws Exception {
        Mockito.when(service.getCacheObjectByKey(any()))
                .thenReturn(CompletableFuture.completedFuture(Collections.emptyList()));

        final ResultActions resultActions = this.mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        resultActions.andExpect(status().isOk());

        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        GetCacheResponse response = new ObjectMapper().readValue(contentAsString, GetCacheResponse.class);

        assertNotNull(response);
        assertEquals(CACHE_RESOURCE_TYPE, response.getData().getType());
    }

    @Test
    public void whenGetEndpointHasReach_throwException() throws Exception {
        final String EXCEPTION_MSG = "not found";
        Mockito.when(service.getCacheObjectByKey(any()))
                .thenThrow(new KeyNotFoundException("not found"));

        final ResultActions resultActions = this.mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        resultActions.andExpect(status().isNotFound());

        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        CommonApiErrorResponse response = new ObjectMapper().readValue(contentAsString, CommonApiErrorResponse.class);

        assertNotNull(response);
        assertEquals(EXCEPTION_MSG, response.getError().getAttributes().getPayload().getErrorMessage());
    }

    @Test
    public void whenPostEndpointHasReach_returnSuccess() throws Exception {
        final String request = new String(Files.readAllBytes(
                Paths.get("src/test/resources/request/objects/add_objects.json")));

        Mockito.when(service.addCacheObject(any(), any()))
                .thenReturn(CompletableFuture.completedFuture(Collections.emptyList()));

        final ResultActions resultActions = this.mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print());
        resultActions.andExpect(status().isCreated());

        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        GetCacheResponse response = new ObjectMapper().readValue(contentAsString, GetCacheResponse.class);

        assertNotNull(response);
        assertEquals(CACHE_RESOURCE_TYPE, response.getData().getType());
    }

    @Test
    public void whenPostEndpointHasReach_throwKeyNotFoundException() throws Exception {
        final String EXCEPTION_MSG = "not found";
        Mockito.when(service.addCacheObject(any(), any()))
                .thenThrow(new KeyNotFoundException("not found"));
        final String request = new String(Files.readAllBytes(
                Paths.get("src/test/resources/request/objects/add_objects.json")));

        final ResultActions resultActions = this.mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print());
        resultActions.andExpect(status().isNotFound());
        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        CommonApiErrorResponse response = new ObjectMapper().readValue(contentAsString, CommonApiErrorResponse.class);

        assertNotNull(response);
        assertEquals(EXCEPTION_MSG, response.getError().getAttributes().getPayload().getErrorMessage());
    }

    @Test
    public void whenPostEndpointHasReach_throwRejectPolicyException() throws Exception {
        Mockito.when(service.addCacheObject(any(), any()))
                .thenThrow(new RejectPolicyException(REJECT_POLICY_MESSAGE));
        final String request = new String(Files.readAllBytes(
                Paths.get("src/test/resources/request/objects/add_objects.json")));

        final ResultActions resultActions = this.mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print());
        resultActions.andExpect(status().isInsufficientStorage());
        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        CommonApiErrorResponse response = new ObjectMapper().readValue(contentAsString, CommonApiErrorResponse.class);

        assertNotNull(response);
        assertEquals(REJECT_POLICY_MESSAGE, response.getError().getAttributes().getPayload().getErrorMessage());
    }

    @Test
    public void whenPutEndpointHasReach_returnSuccess() throws Exception {
        final String request = new String(Files.readAllBytes(
                Paths.get("src/test/resources/request/objects/update_object.json")));

        Mockito.when(service.updateObject(any(), any()))
                .thenReturn(CompletableFuture.completedFuture(new JSONObject()));

        final ResultActions resultActions = this.mockMvc.perform(put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print());
        resultActions.andExpect(status().isOk());

        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        GetCacheResponse response = new ObjectMapper().readValue(contentAsString, GetCacheResponse.class);

        assertNotNull(response);
        assertEquals(CACHE_RESOURCE_TYPE, response.getData().getType());
    }

    @Test
    public void whenPutEndpointHasReach_throwKeyNotFoundException() throws Exception {
        final String EXCEPTION_MSG = "not found";
        Mockito.when(service.updateObject(any(), any()))
                .thenThrow(new KeyNotFoundException("not found"));
        final String request = new String(Files.readAllBytes(
                Paths.get("src/test/resources/request/objects/update_object.json")));

        final ResultActions resultActions = this.mockMvc.perform(put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print());
        resultActions.andExpect(status().isNotFound());
        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        CommonApiErrorResponse response = new ObjectMapper().readValue(contentAsString, CommonApiErrorResponse.class);

        assertNotNull(response);
        assertEquals(EXCEPTION_MSG, response.getError().getAttributes().getPayload().getErrorMessage());
    }

    @Test
    public void whenPatchEndpointHasReach_returnSuccess() throws Exception {
        final String request = new String(Files.readAllBytes(
                Paths.get("src/test/resources/request/objects/patch_object.json")));

        Mockito.when(service.patchObject(any(), any()))
                .thenReturn(CompletableFuture.completedFuture(new JSONObject()));

        final ResultActions resultActions = this.mockMvc.perform(patch(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print());
        resultActions.andExpect(status().isOk());

        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        GetCacheResponse response = new ObjectMapper().readValue(contentAsString, GetCacheResponse.class);

        assertNotNull(response);
        assertEquals(CACHE_RESOURCE_TYPE, response.getData().getType());
    }

    @Test
    public void whenPatchEndpointHasReach_throwKeyNotFoundException() throws Exception {
        final String EXCEPTION_MSG = "not found";
        Mockito.when(service.patchObject(any(), any()))
                .thenThrow(new KeyNotFoundException("not found"));
        final String request = new String(Files.readAllBytes(
                Paths.get("src/test/resources/request/objects/patch_object.json")));

        final ResultActions resultActions = this.mockMvc.perform(patch(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print());
        resultActions.andExpect(status().isNotFound());
        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        CommonApiErrorResponse response = new ObjectMapper().readValue(contentAsString, CommonApiErrorResponse.class);

        assertNotNull(response);
        assertEquals(EXCEPTION_MSG, response.getError().getAttributes().getPayload().getErrorMessage());
    }

    @Test
    public void whenDeleteEndpointHasReach_throwKeyNotFoundException() throws Exception {
        final String EXCEPTION_MSG = "not found";
        Mockito.when(service.deleteObject(any(), any()))
                .thenThrow(new KeyNotFoundException("not found"));
        final String request = new String(Files.readAllBytes(
                Paths.get("src/test/resources/request/objects/delete_object.json")));

        final ResultActions resultActions = this.mockMvc.perform(delete(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print());
        resultActions.andExpect(status().isNotFound());
        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        CommonApiErrorResponse response = new ObjectMapper().readValue(contentAsString, CommonApiErrorResponse.class);

        assertNotNull(response);
        assertEquals(EXCEPTION_MSG, response.getError().getAttributes().getPayload().getErrorMessage());
    }

    @Test
    public void whenGetEndpointHasReach_withoutDependencyInjection_throwException() throws Exception {
        final String EXCEPTION_MSG = "Unknown Error - null";
        final ResultActions resultActions = this.mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        resultActions.andExpect(status().is5xxServerError());

        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        CommonApiErrorResponse response = new ObjectMapper().readValue(contentAsString, CommonApiErrorResponse.class);

        assertNotNull(response);
        assertEquals(EXCEPTION_MSG, response.getError().getAttributes().getPayload().getErrorMessage());
    }
}