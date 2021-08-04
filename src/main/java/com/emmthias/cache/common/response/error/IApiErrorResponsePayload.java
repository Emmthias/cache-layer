package com.emmthias.cache.common.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Interface for every api error response to enforce following the structure
 * similar to the JSON API structure. This one is to mimic the 'payload' json
 * structure within the attributes field. 'payload' contain the actual business
 * error response data for the request from the client
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "IApiErrorResponsePayload", description = "Interface for every api error response to enforce " +
        "following the structure similar to the JSON API structure. This one is to mimic the 'payload' json structure" +
        " within the attributes field. 'payload' contain the actual business error response data for the request from" +
        " the client")
public interface IApiErrorResponsePayload extends Serializable {

    /**
     * An error code of its application for the current scenario. example is,
     * 'ERR_0001' or any other pattern decided by the api/service provider
     *
     * @return String - An error code of its application for the current scenario
     */
    @ApiModelProperty(required = false, value = "An error code if its application for the current scenario",
            example = "ERR_0001")
    String getErrorCode();

    /**
     * An error message which describes the business error to the clients clearly so
     * that they understand what happened and what they need to do to rectify it in
     * case its a client error condition. This field can't be null or empty.
     *
     * @return String - An error message which describes the business error to the
     * clients clearly so that they understand what happened and what they
     * need to do to rectify it in case its a client error condition
     */
    @ApiModelProperty(required = true, value = "An error message which describes the business error to the clients " +
            "clearly so that they understand what happened and what they need to do to rectify it in case its a " +
            "client error condition", allowEmptyValue = false)
    String getErrorMessage();

    /**
     * If its a client error, it would be a good practise to advise or recommend
     * some action for the clients if followed by them the error will not occur in
     * the future
     *
     * @return String - If its a client error, it would be a good practise to advise
     * or recommend some action for the clients if followed by them the
     * error will not occur in the future
     */
    @ApiModelProperty(required = false, value = "If its a client error, it would be a good practise to advise or " +
            "recommend some action for the clients if followed by them the error will not occur in the future")
    String getRecommendedAction();

    /**
     * The entire trace of the error across multiple system calls can be sent at
     * this field
     *
     * @return String - The entire trace of the error across multiple system calls
     * can be sent at this field
     */
    @ApiModelProperty(required = false, value = "The entire trace of the error across multiple system calls can be " +
            "sent at this field")
    String getErrorTrace();
}
