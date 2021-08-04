package com.emmthias.cache.common.response.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Top level interface for every api response to enforce following the structure
 * similar to the JSON API structure. 'data' and 'meta' are the attributes of
 * the top level response json.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "IApiSuccessResponse", description = "Top level interface for every api response to enforce " +
        "following the structure similar to the JSON API structure. 'data' and 'meta' are the attributes of the top " +
        "level response json.")
public interface IApiSuccessResponse extends IApiResponse {

    /**
     * API success response's 'data' attribute related to the JSON API specification
     * guidelines.This field can't be null or empty.
     *
     * @return IApiSuccessResponseData - API success response's 'data' attribute
     * related to the JSON API specification guidelines
     */
    @ApiModelProperty(required = true, allowEmptyValue = false)
    IApiSuccessResponseData getData();
}
