package com.emmthias.cache.common.response.error;

import com.emmthias.cache.common.response.error.IApiErrorResponseData;
import com.emmthias.cache.common.response.success.IApiResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Top level interface for every api error response to enforce following the
 * structure similar to the JSON API structure. 'error' and 'meta' are the
 * attributes of the top level error response json. *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "IApiErrorResponse", description = "Top level interface for every api error response to enforce " +
        "following the structure similar to the JSON API structure. 'error' and 'meta' are the attributes of the top " +
        "level error response json.")
public interface IApiErrorResponse extends IApiResponse {

    /**
     * API success response's 'error' attribute related to the JSON API
     * specification guidelines. This field can't be null or empty.
     *
     * @return IApiErrorResponseData - API success response's 'error' attribute
     *         related to the JSON API specification guidelines
     */
    @ApiModelProperty(required = true, allowEmptyValue = false)
    IApiErrorResponseData getError();
}
