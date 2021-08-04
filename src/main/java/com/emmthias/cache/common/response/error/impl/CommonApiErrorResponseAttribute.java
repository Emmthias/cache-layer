package com.emmthias.cache.common.response.error.impl;

import com.emmthias.cache.common.response.error.IApiErrorResponseAttribute;
import com.emmthias.cache.common.response.error.IApiErrorResponsePayload;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"payload"})
@JsonDeserialize(builder = CommonApiErrorResponseAttribute.Builder.class)
public class CommonApiErrorResponseAttribute implements IApiErrorResponseAttribute {

    private static final long serialVersionUID = -776203961349634982L;

    @JsonProperty("payload")
    private final CommonApiErrorResponsePayload payload;

    /**
     * @param builder
     */
    private CommonApiErrorResponseAttribute(Builder builder) {
        if (null == builder.payload) {
            throw new IllegalArgumentException("payload field can't be null or empty");
        }

        this.payload = builder.payload;
    }

    /**
     * Creates builder to build {@link CommonApiErrorResponseAttribute}.
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
     * ApiErrorResponseAttribute#getPayload()
     */
    @Override
    public IApiErrorResponsePayload getPayload() {
        return payload;
    }

    /**
     * Builder to build {@link CommonApiErrorResponseAttribute}.
     */
    public static final class Builder {
        private CommonApiErrorResponsePayload payload;

        private Builder() {
        }

        public Builder withPayload(CommonApiErrorResponsePayload payload) {
            if (null == payload) {
                throw new IllegalArgumentException("payload field can't be null or empty");
            }

            this.payload = payload;
            return this;
        }

        public CommonApiErrorResponseAttribute build() {
            return new CommonApiErrorResponseAttribute(this);
        }
    }
}