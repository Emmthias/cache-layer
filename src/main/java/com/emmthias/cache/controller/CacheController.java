package com.emmthias.cache.controller;

import com.emmthias.cache.service.ICacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.emmthias.cache.constants.Constants.CACHE_SERVICES_ENDPOINT;
import static com.emmthias.cache.constants.Constants.CACHE_URL;
import static com.emmthias.cache.constants.Constants.KEY;
import static com.emmthias.cache.constants.Constants.REQUEST_TYPE_DELETE;
import static com.emmthias.cache.constants.Constants.REQUEST_TYPE_GET;
import static com.emmthias.cache.constants.Constants.REQUEST_TYPE_POST;
import static com.emmthias.cache.constants.Constants.REQUEST_TYPE_PATCH;

/**
 * Controller to handle cache service
 * <p>
 * End point that handle the cache service operations.
 * </p>
 */
@RestController
@RequestMapping({CACHE_SERVICES_ENDPOINT})
@Api("cache service operation controller")
public class CacheController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    ICacheService ICacheService;

    @ApiOperation(value = "This end point will return the object stored at {key} if the object is not expired.",
            httpMethod = REQUEST_TYPE_GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "The elements belongs to a cache layer identifying by key.",
                    response = String.class),
            @ApiResponse(code = 404,
                    message = "Not found element",
                    response = String.class)})
    @GetMapping(name = "Retrieve cache object by key",
            value = {CACHE_URL},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getObject(
            @ApiParam(
                    required = true,
                    name = "key",
                    value = "the key identifier")
            @PathVariable(KEY) String key) throws JSONException {
        return new JSONObject().put("tmp", "test").toString();
    }

    @ApiOperation(value = "This end point will insert the {object} provided in the body of the request into a slot in" +
            "memory at {key}",
            httpMethod = REQUEST_TYPE_POST)
    @ApiResponses(value = {
            @ApiResponse(code = 201,
                    message = "The created object added into a cache layer.",
                    response = String.class),
            @ApiResponse(code = 507,
                    message = "The cache layer has Insufficient Storage.",
                    response = String.class)
    })
    @PostMapping(name = "Create an cache object by key",
            value = {CACHE_URL},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String addObject(
            @ApiParam(
                    required = true,
                    name = "key",
                    value = "the key identifier")
            @PathVariable(KEY) String key,
            @RequestBody @ApiParam(required = true,
                    allowEmptyValue = false, name = "codeDetailRequest", value = "The request body content ")
                    JSONObject element) throws JSONException {
        return new JSONObject().put("tmp", "test").toString();

    }

    @ApiOperation(value = "This end point will patch the {object} provided in the body of the request into their " +
            "corresponding slot in memory at {key}",
            httpMethod = REQUEST_TYPE_PATCH)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "The element associated to cache layer identifying by key",
                    response = String.class)})
    @PutMapping(name = "", value = {CACHE_URL}, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String patchObject(
            @ApiParam(
                    required = true,
                    name = "key",
                    value = "the key identifier")
            @PathVariable(KEY) String key,
            @RequestBody @ApiParam(required = true,
                    allowEmptyValue = false, name = "codeDetailRequest", value = "The request body content ")
                    JSONObject element) throws JSONException {
        return new JSONObject().put("tmp", "test").toString();
    }

    @ApiOperation(value = "This end point will delete the object stored at slot {key}.",
            httpMethod = REQUEST_TYPE_DELETE)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "The deleted element to a cache layer identifying by key",
                    response = String.class)})
    @DeleteMapping(name = "This end point will delete the object stored at slot {key}", value = {CACHE_URL}, consumes =
            MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteObject(
            @ApiParam(
                    required = true,
                    name = "key",
                    value = "the key identifier")
            @PathVariable(KEY) String key,
            @RequestBody @ApiParam(required = true,
                    allowEmptyValue = false, name = "codeDetailRequest", value = "The request body content ")
                    JSONObject element) throws JSONException {
        return new JSONObject().put("tmp", "test").toString();
    }

}
