package com.emmthias.cache.common.response.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Interface for every api response to enforce following the structure similar
 * to the JSON API structure. This one is to mimic the 'attributes' json
 * structure within the 'data' field. 'attributes' contain the actual 'payload'
 * of the business response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "IApiSuccessResponseAttribute", description = "Interface for every api response to enforce following" +
        " the structure similar to the JSON API structure. This one is to mimic the 'attributes' json structure " +
        "within the 'data' field. 'attributes' contain the actual 'payload' of the business response")
public interface IApiSuccessResponseAttribute extends Serializable {

    /**
     * The actual business payload to be returned by the api. This field can't be
     * null or empty.
     *
     * @return IApiSuccessResponsePayload - API success response's 'payload'
     * attribute related to the JSON API specification guidelines
     */
    @ApiModelProperty(required = true, allowEmptyValue = false)
    IApiSuccessResponsePayload getPayload();
}
