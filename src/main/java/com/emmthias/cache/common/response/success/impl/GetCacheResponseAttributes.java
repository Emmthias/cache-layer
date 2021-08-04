package com.emmthias.cache.common.response.success.impl;

import com.emmthias.cache.common.response.success.IApiSuccessResponseAttribute;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static java.util.Objects.isNull;

/**
 * Represents the GetCacheResponseAttributes model.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"payload"})
@JsonDeserialize(builder = GetCacheResponseAttributes.Builder.class)
@ApiModel("GetCacheResponseAttributes")
public class GetCacheResponseAttributes implements IApiSuccessResponseAttribute {
    private static final long serialVersionUID = 2340740004160892087L;

    @JsonProperty("payload")
    @ApiModelProperty(required = true, allowEmptyValue = false, value = "cache details")
    private final GetCacheResponsePayload payload;

    private GetCacheResponseAttributes(Builder builder) {
        if (isNull(builder.payload)) {
            throw new IllegalArgumentException("payload field can't be null");
        }

        this.payload = builder.payload;
    }

    /**
     * Creates builder to build {@link GetCacheResponseAttributes}.
     *
     * @return created builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public GetCacheResponsePayload getPayload() {
        return this.payload;
    }

    /**
     * Builder to build {@link GetCacheResponseAttributes}.
     */
    public static final class Builder {
        private GetCacheResponsePayload payload;

        private Builder() {
        }

        /**
         * Sets the Payload.
         *
         * @param payload The Payload value.
         * @return the builder.
         */
        public Builder withPayload(GetCacheResponsePayload payload) {
            if (isNull(payload)) {
                throw new IllegalArgumentException("payload cannot be null");
            }

            this.payload = payload;
            return this;
        }

        public GetCacheResponseAttributes build() {
            return new GetCacheResponseAttributes(this);
        }
    }
}
