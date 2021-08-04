package com.emmthias.cache.common.response.error.impl;

import com.emmthias.cache.common.response.error.IApiErrorResponse;
import com.emmthias.cache.common.response.error.IApiErrorResponseData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"error"})
@JsonDeserialize(builder = CommonApiErrorResponse.Builder.class)
public class CommonApiErrorResponse implements IApiErrorResponse {

    
    private static final long serialVersionUID = 2968758948534789459L;

    
    @JsonProperty("error")
    private final CommonApiErrorResponseData error;

    /**
     * @param builder
     */
    private CommonApiErrorResponse(Builder builder) {
        if (null == builder.error) {
            throw new IllegalArgumentException("error field can't be null or empty");
        }

        this.error = builder.error;
    }

    /**
     * Creates builder to build {@link CommonApiErrorResponse}.
     *
     * @return created builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.dowjones.mosaic.mirage.api.contracts.common.response.error.
     * ApiErrorResponse#getError()
     */
    @Override
    public IApiErrorResponseData getError() {
        return error;
    }

    /**
     * Builder to build {@link CommonApiErrorResponse}.
     */
    public static final class Builder {
        private CommonApiErrorResponseData error;

        private Builder() {
        }

        public Builder withError(CommonApiErrorResponseData error) {
            if (null == error) {
                throw new IllegalArgumentException("error field can't be null or empty");
            }

            this.error = error;
            return this;
        }

        public CommonApiErrorResponse build() {
            return new CommonApiErrorResponse(this);
        }
    }
}