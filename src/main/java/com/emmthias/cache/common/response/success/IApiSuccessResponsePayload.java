package com.emmthias.cache.common.response.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * Interface for every api response to enforce following the structure similar
 * to the JSON API structure. This one is to mimic the 'payload' json structure
 * within the 'attributes' field. 'payload' contain the actual business response
 * data for the request from the client. The users of this interface know better
 * on how to define their payload for the requirement in hand. They just need to
 * make sure to create implementation of this interface to make use of the
 * features provided by the service framework. So this acts like a marker
 * interface for the service framework.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "IApiSuccessResponsePayload", description = "Interface for every api response to enforce following " +
        "the structure similar to the JSON API structure. This one is to mimic the 'payload' json structure within " +
        "the 'attributes' field. 'payload' contain the actual business response data for the request from the client." +
        " The users of this interface know better on how to define their payload for the requirement in hand. They " +
        "just need to make sure to create implementation of this interface to make use of the features provided by " +
        "the service framework. So this acts like a marker interface for the service framework")
public interface IApiSuccessResponsePayload extends Serializable {
}
