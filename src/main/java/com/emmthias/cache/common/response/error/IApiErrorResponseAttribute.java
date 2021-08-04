package com.emmthias.cache.common.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Interface for every api error response to enforce following the structure
 * similar to the JSON API structure. This one is to mimic the 'attributes' json
 * structure within the errors field. 'attributes' contain the actual 'payload'
 * of the error response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "IApiErrorResponseAttribute", description = "Interface for every api error response to enforce " +
        "following the structure similar to the JSON API structure. This one is to mimic the 'attributes' json " +
        "structure within the errors field. 'attributes' contain the actual 'payload' of the error response")
public interface IApiErrorResponseAttribute extends Serializable {

    /**
     * The actual error payload to be returned by the api. This field can't be null or empty.
     *
     * @return IApiErrorResponsePayload - API error response's 'payload' attribute
     * related to the JSON API specification guidelines
     */
    @ApiModelProperty(required = true, allowEmptyValue = false)
    IApiErrorResponsePayload getPayload();
}
