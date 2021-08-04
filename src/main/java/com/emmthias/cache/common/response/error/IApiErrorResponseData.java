package com.emmthias.cache.common.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Interface for every api error response to enforce following the structure
 * similar to the JSON API structure. This one is to mimic the 'error' json
 * structure within the top level response json. 'error' contains the 'id' and
 * 'attributes' fields
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "IApiErrorResponseData", description = "Interface for every api error response to enforce following " +
        "the structure similar to the JSON API structure. This one is to mimic the 'error' json structure within the " +
        "top level response json. 'error' contains the 'id' and 'attributes' fields")
public interface IApiErrorResponseData extends Serializable {

    /**
     * This is the server generated resource identifier sent in the response. This
     * field can't be null or empty.
     *
     * @return String - API error response's 'id' attribute related to the JSON API
     * specification guidelines
     */
    @ApiModelProperty(required = true, allowEmptyValue = false, value = "This is the server generated resource " +
            "identifier sent in the response.")
    String getId();

    /**
     * API error response's 'attributes' attribute related to the JSON API
     * specification guidelines. 'attributes' field can't be null or empty
     *
     * @return IApiErrorResponseAttribute - API error response's 'attributes'
     * attribute related to the JSON API specification guidelines
     */
    @ApiModelProperty(required = true, allowEmptyValue = false)
    IApiErrorResponseAttribute getAttributes();
}
