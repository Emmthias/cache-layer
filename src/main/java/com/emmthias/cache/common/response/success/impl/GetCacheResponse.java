package com.emmthias.cache.common.response.success.impl;

import com.emmthias.cache.common.response.success.IApiSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static java.util.Objects.isNull;

/**
 * Represents the GetCacheResponse model.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"data"})
@JsonDeserialize(builder = GetCacheResponse.Builder.class)
@ApiModel("GetCacheResponse")
public class GetCacheResponse implements IApiSuccessResponse {
    private static final long serialVersionUID = -2891440551570316253L;

    @JsonProperty("data")
    @ApiModelProperty(required = true, allowEmptyValue = false, value = "Data for the access cache details")
    private final GetCacheResponseData data;

    private GetCacheResponse(Builder builder) {
        if (isNull(builder.data)) {
            throw new IllegalArgumentException("data fields can't be null");
        }

        this.data = builder.data;
    }

    /**
     * Creates builder to build {@link GetCacheResponse}.
     *
     * @return created builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public GetCacheResponseData getData() {
        return data;
    }

    /**
     * Builder to build {@link GetCacheResponse}.
     */
    public static final class Builder {
        private GetCacheResponseData data;

        private Builder() {
        }

        /**
         * Sets the Data.
         *
         * @param data The data value.
         * @return the builder.
         */
        public Builder withData(GetCacheResponseData data) {
            if (isNull(data)) {
                throw new IllegalArgumentException("data cannot be null");
            }

            this.data = data;
            return this;
        }

        public GetCacheResponse build() {
            return new GetCacheResponse(this);
        }
    }
}
