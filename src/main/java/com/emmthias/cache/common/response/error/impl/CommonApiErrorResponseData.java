package com.emmthias.cache.common.response.error.impl;

import com.emmthias.cache.common.response.error.IApiErrorResponseAttribute;
import com.emmthias.cache.common.response.error.IApiErrorResponseData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import static com.emmthias.cache.validator.ValidatorUtil.isBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "attributes"})
@JsonDeserialize(builder = CommonApiErrorResponseData.Builder.class)
public class CommonApiErrorResponseData implements IApiErrorResponseData {

    private static final long serialVersionUID = -7254216489759356264L;

    @JsonProperty("id")
    private final String id;

    @JsonProperty("attributes")
    private final CommonApiErrorResponseAttribute attributes;

    /**
     * @param builder
     */
    private CommonApiErrorResponseData(Builder builder) {
        if (null == builder.id || null == builder.attributes) {
            throw new IllegalArgumentException("id, attributes fields can't be null or empty");
        }

        this.id = builder.id;
        this.attributes = builder.attributes;
    }

    /**
     * Creates builder to build {@link CommonApiErrorResponseData}.
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
     * ApiErrorResponseData#getId()
     */
    @Override
    public String getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.dowjones.mosaic.mirage.api.contracts.common.response.error.
     * ApiErrorResponseData#getAttributes()
     */
    @Override
    public IApiErrorResponseAttribute getAttributes() {
        return attributes;
    }

    /**
     * Builder to build {@link CommonApiErrorResponseData}.
     */
    public static final class Builder {
        private String id;
        private CommonApiErrorResponseAttribute attributes;

        private Builder() {
        }

        public Builder withId(String id) {
            if (isBlank(id)) {
                throw new IllegalArgumentException("id field can't be null or empty");
            }

            this.id = id;
            return this;
        }

        public Builder withAttributes(CommonApiErrorResponseAttribute attributes) {
            if (null == attributes) {
                throw new IllegalArgumentException("attributes field can't be null or empty");
            }

            this.attributes = attributes;
            return this;
        }

        public CommonApiErrorResponseData build() {
            return new CommonApiErrorResponseData(this);
        }
    }
}