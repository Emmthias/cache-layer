package com.emmthias.cache.controller;


import com.emmthias.cache.common.response.error.IApiErrorResponse;
import com.emmthias.cache.common.response.success.IApiResponse;
import com.emmthias.cache.common.response.success.impl.GetCacheResponse;
import com.emmthias.cache.domain.Cache;
import com.emmthias.cache.domain.CacheObject;
import com.emmthias.cache.domain.CachePreference;
import com.emmthias.cache.service.ICacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.emmthias.cache.constants.Constants.CACHE_BUCKET_BY_KEY_URL;
import static com.emmthias.cache.constants.Constants.CACHE_BUCKET_URL;
import static com.emmthias.cache.constants.Constants.CACHE_SERVICE_ENDPOINT;
import static com.emmthias.cache.constants.Constants.KEY;
import static com.emmthias.cache.constants.Constants.REQUEST_TYPE_DELETE;
import static com.emmthias.cache.constants.Constants.REQUEST_TYPE_GET;
import static com.emmthias.cache.constants.Constants.REQUEST_TYPE_POST;
import static com.emmthias.cache.converter.Converters.ConvertToJsonElement;
import static com.emmthias.cache.converter.Converters.buildGetCacheResponse;
import static com.emmthias.cache.converter.Converters.convertMapIntoJsonStructure;
import static com.emmthias.cache.converter.Converters.convertMapToCacheObject;
import static com.emmthias.cache.validator.ValidatorUtil.validateCachePreference;

/**
 * Controller to handle cache bucket service
 * <p>
 * End point that handle the cache service operations.
 * </p>
 */

@RestController
@RequestMapping({CACHE_SERVICE_ENDPOINT})
@Api("cache service operation controller")
public class CacheBucketController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheBucketController.class);

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
                    message = "The cache layer has Insufficient Storage. applies for REJECT eviction cache policy " +
                            "when the elements reach the slot number",
                    response = IApiErrorResponse.class),
            @ApiResponse(code = 509,
                    message = "conflict due an existing key in memory cache",
                    response = IApiErrorResponse.class),
    })
    @PostMapping(name = "Create an cache object by key",
            value = {CACHE_BUCKET_BY_KEY_URL},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IApiResponse> createBucketByKey(
            @ApiParam(required = true, name = "key", value = "the key identifier")
            @PathVariable(KEY) String key,
            @RequestBody @ApiParam(required = true,
                    allowEmptyValue = false, name = "data", value = "The request body content ")
                    Cache element) throws JSONException, ExecutionException, InterruptedException {
        CachePreference preference = validateCachePreference(element.getCachePreference());
        List<CacheObject> data = convertMapToCacheObject(element.getElements());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(buildGetCacheResponse(cacheService.createCacheObject(key, data, preference).get()));
    }

    @ApiOperation(value = "This end point will return all the cache objects.",
            httpMethod = REQUEST_TYPE_GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "The elements belongs to a cache layer identifying by key.",
                    response = GetCacheResponse.class),
            @ApiResponse(code = 404,
                    message = "Not found element",
                    response = IApiErrorResponse.class)})
    @GetMapping(name = "Retrieve cache object by key",
            value = {CACHE_BUCKET_URL},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IApiResponse> getAllBuckets() throws JSONException, ExecutionException, InterruptedException {

        return ResponseEntity.status(HttpStatus.OK)
                .body(buildGetCacheResponse(
                        convertMapIntoJsonStructure(cacheService.getMapInstance().get()))
                );
    }

    @ApiOperation(value = "This end point will delete the object stored at slot {key}.",
            httpMethod = REQUEST_TYPE_DELETE)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "The deleted element to a cache layer identifying by key",
                    response = GetCacheResponse.class),
            @ApiResponse(code = 404,
                    message = "Not found element",
                    response = IApiErrorResponse.class)
    })
    @DeleteMapping(name = "This end point will delete the object stored at slot {key}",
            value = {CACHE_BUCKET_BY_KEY_URL},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IApiResponse> deleteBucketByKey(
            @ApiParam(required = true, name = "key", value = "the key identifier")
            @PathVariable(KEY) String key,
            @RequestBody @ApiParam(required = true,
                    allowEmptyValue = false, name = "cacheDetailRequest", value = "The request body content ")
                    JSONObject element) throws JSONException, ExecutionException, InterruptedException {
        return ResponseEntity.status(HttpStatus.OK).body(
                buildGetCacheResponse(
                        ConvertToJsonElement("response", cacheService.delete(key).get()))
        );
    }
}
