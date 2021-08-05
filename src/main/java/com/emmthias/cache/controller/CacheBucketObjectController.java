package com.emmthias.cache.controller;

import com.emmthias.cache.common.response.error.IApiErrorResponse;
import com.emmthias.cache.common.response.success.IApiResponse;
import com.emmthias.cache.common.response.success.impl.GetCacheResponse;
import com.emmthias.cache.domain.Cache;
import com.emmthias.cache.domain.CacheObject;
import com.emmthias.cache.service.ICacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.emmthias.cache.constants.Constants.CACHE_OBJECT_URL;
import static com.emmthias.cache.constants.Constants.CACHE_SERVICE_BUCKET_ENDPOINT;
import static com.emmthias.cache.constants.Constants.KEY;
import static com.emmthias.cache.constants.Constants.REQUEST_TYPE_DELETE;
import static com.emmthias.cache.constants.Constants.REQUEST_TYPE_GET;
import static com.emmthias.cache.constants.Constants.REQUEST_TYPE_PATCH;
import static com.emmthias.cache.constants.Constants.REQUEST_TYPE_POST;
import static com.emmthias.cache.constants.Constants.REQUEST_TYPE_PUT;
import static com.emmthias.cache.converter.Converters.ConvertToJsonElement;
import static com.emmthias.cache.converter.Converters.buildGetCacheResponse;
import static com.emmthias.cache.converter.Converters.convertMapToCacheObject;
import static com.emmthias.cache.converter.Converters.convertMapToSingleCacheObject;

/**
 * Controller to handle cache object service
 * <p>
 * End point that handle the cache object service operations.
 * </p>
 */

@RestController
@RequestMapping({CACHE_SERVICE_BUCKET_ENDPOINT})
@Api("cache service operation controller")
public class CacheBucketObjectController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheBucketObjectController.class);

    @Autowired
    ICacheService cacheService;

    @ApiOperation(value = "This end point will insert the {object} provided in the body of the request into a slot in" +
            "memory at {key}",
            httpMethod = REQUEST_TYPE_POST)
    @ApiResponses(value = {
            @ApiResponse(code = 201,
                    message = "The created object added into a cache layer.",
                    response = GetCacheResponse.class),
            @ApiResponse(code = 507,
                    message = "The cache layer has Insufficient Storage.",
                    response = IApiErrorResponse.class),
            @ApiResponse(code = 404,
                    message = "Not found element",
                    response = IApiErrorResponse.class)
    })
    @PostMapping(name = "Create an cache object by key",
            value = {CACHE_OBJECT_URL},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IApiResponse> addBucketObjectByKey(
            @ApiParam(required = true, name = "key", value = "the key identifier")
            @PathVariable(KEY) String key,
            @RequestBody @ApiParam(required = true,
                    allowEmptyValue = false, name = "data", value = "The request body content ")
                    Cache element) throws JSONException, ExecutionException, InterruptedException {
        List<CacheObject> data = convertMapToCacheObject(element.getElements());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(buildGetCacheResponse(cacheService.addCacheObject(key, data).get()));
    }

    @ApiOperation(value = "This end point will return the object stored at {key} if the object is not expired.",
            httpMethod = REQUEST_TYPE_GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "The elements belongs to a cache layer identifying by key.",
                    response = GetCacheResponse.class),
            @ApiResponse(code = 404,
                    message = "Not found element",
                    response = IApiErrorResponse.class)})
    @GetMapping(name = "Retrieve cache object by key",
            value = {CACHE_OBJECT_URL},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IApiResponse> getBucketByKey(
            @ApiParam(required = true, name = "key", value = "the key identifier")
            @PathVariable(KEY) String key) throws JSONException, ExecutionException, InterruptedException {

        return ResponseEntity.status(HttpStatus.OK)
                .body(buildGetCacheResponse(cacheService.getCacheObjectByKey(key).get()));
    }

    @ApiOperation(value = "This end point will patch the {object} provided in the body making the merge of " +
            "store information with new value in the {object} of the request into their corresponding slot in memory " +
            "at {key}",
            httpMethod = REQUEST_TYPE_PATCH)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "The element associated to cache layer identifying by key",
                    response = GetCacheResponse.class),
            @ApiResponse(code = 404,
                    message = "Not found element",
                    response = IApiErrorResponse.class)})
    @PatchMapping(name = "patch", value = {CACHE_OBJECT_URL}, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IApiResponse> patchBucketObjectByKey(
            @ApiParam(required = true, name = "key", value = "the key identifier")
            @PathVariable(KEY) String key,
            @RequestBody @ApiParam(required = true,
                    allowEmptyValue = false, name = "cacheDetailRequest", value = "The request body content ")
                    Cache element) throws JSONException, ExecutionException, InterruptedException {
        CacheObject elementToPatch = convertMapToSingleCacheObject(element.getElement());
        return ResponseEntity.status(HttpStatus.OK)
                .body(buildGetCacheResponse(cacheService.patchObject(key, elementToPatch).get()));
    }

    @ApiOperation(value = "This end point will update the {object} provided in the body of the request into their" +
            "corresponding slot in memory at {key}",
            httpMethod = REQUEST_TYPE_PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "The element associated to cache layer identifying by key",
                    response = GetCacheResponse.class),
            @ApiResponse(code = 404,
                    message = "Not found element",
                    response = IApiErrorResponse.class)})
    @PutMapping(name = "put", value = {CACHE_OBJECT_URL}, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IApiResponse> updateBucketObjectByKey(
            @ApiParam(required = true, name = "key", value = "the key identifier")
            @PathVariable(KEY) String key,
            @RequestBody @ApiParam(required = true,
                    allowEmptyValue = false, name = "cacheDetailRequest", value = "The request body content ")
                    Cache element) throws JSONException, ExecutionException, InterruptedException {
        CacheObject elementToUpdate = convertMapToSingleCacheObject(element.getElement());
        return ResponseEntity.status(HttpStatus.OK)
                .body(buildGetCacheResponse(cacheService.updateObject(key, elementToUpdate).get()));
    }

    @ApiOperation(value = "This end point will delete the {object} provided in the body of the request into their" +
            "corresponding slot in memory at {key}",
            httpMethod = REQUEST_TYPE_DELETE)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "The element associated to cache layer identifying by key",
                    response = GetCacheResponse.class),
            @ApiResponse(code = 404,
                    message = "Not found element",
                    response = IApiErrorResponse.class)})
    @DeleteMapping(name = "delete", value = {CACHE_OBJECT_URL}, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IApiResponse> deleteBucketObjectByKey(
            @ApiParam(required = true, name = "key", value = "the key identifier")
            @PathVariable(KEY) String key,
            @RequestBody @ApiParam(required = true,
                    allowEmptyValue = false, name = "cacheDetailRequest", value = "The request body content ")
                    Cache element) throws JSONException, ExecutionException, InterruptedException {
        CacheObject elementToUpdate = convertMapToSingleCacheObject(element.getElement());
        return ResponseEntity.status(HttpStatus.OK)
                .body(buildGetCacheResponse(ConvertToJsonElement("response", cacheService.deleteObject(key,
                        elementToUpdate).get())));
    }
}
