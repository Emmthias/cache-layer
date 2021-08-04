package com.emmthias.cache.common.response.success;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Interface for every api response to enforce following the structure similar
 * to the JSON API structure. This one is to mimic the 'data' json structure
 * within the top level response json. 'data' contains the 'id', 'attributes'
 * and 'type' as per JSON API
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "IApiSuccessResponseData", description = "Interface for every api response to enforce following the" +
        " structure similar to the JSON API structure. This one is to mimic the 'data' json structure within the top " +
        "level response json. 'data' contains the 'id', 'attributes' and 'type' as per JSON API")
public interface IApiSuccessResponseData extends Serializable {

    /**
     * This is the server generated resource identifier sent in the response. This
     * field can't be null or empty for the response related to a single resource.
     * But for responses related to multiple resources populate this field with a
     * random uuid instead of resource identifier.
     *
     * @return String - API success response's 'id' attribute related to the JSON
     * API specification guidelines
     */
    @ApiModelProperty(required = true, value = "This is the server generated resource identifier sent in the response" +
            ". For multiple resource responses, this field can be populated with a random uuid, but for the responses" +
            "with single requests populate this with the resource identifier")
    String getId();

    /**
     * API success response's 'attributes' attribute related to the JSON API
     * specification guidelines
     *
     * @return IApiSuccessResponseAttribute - API success response's 'attributes'
     * attribute related to the JSON API specification guidelines
     */
    @ApiModelProperty(required = false)
    IApiSuccessResponseAttribute getAttributes();

    /**
     * The resource type. examples are like 'bucket creation (or) retrieve bucket (or) etc... '.
     * This field can't be null or empty.
     *
     * @return String - API success response's 'type' attribute related to the JSON
     * API specification guidelines
     */
    @ApiModelProperty(required = true, allowEmptyValue = false, value = "The resource type", example = "bucket " +
            "creation (or) retrieve bucket (or) etc... ")
    String getType();
}
