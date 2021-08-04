package com.emmthias.cache.common.response.success.impl;

import com.emmthias.cache.common.response.success.IApiSuccessResponseData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.emmthias.cache.validator.ValidatorUtil.isBlank;
import static java.util.Objects.isNull;

/**
 * Represents the GetCacheResponseData model.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "attributes", "type"})
@JsonDeserialize(builder = GetCacheResponseData.Builder.class)
@ApiModel("GetCacheResponseData")
public class GetCacheResponseData implements IApiSuccessResponseData {
    private static final long serialVersionUID = -5616958983493515987L;

    @JsonProperty("id")
    @ApiModelProperty(required = true, allowEmptyValue = false,
            value = "The identifier of the resource", example = "1")
    private final String id;

    @JsonProperty("attributes")
    @ApiModelProperty(required = true, allowEmptyValue = false,
            value = "The resource attributes")
    private final GetCacheResponseAttributes attributes;

    @JsonProperty("type")
    @ApiModelProperty(required = true, allowEmptyValue = false,
            value = "The resource type", example = "bucket")
    private final String type;

    private GetCacheResponseData(Builder builder) {
        if (isBlank(builder.id) || isBlank(builder.type) || isNull(builder.attributes)) {
            throw new IllegalArgumentException("id, type, attributes fields can't be null or empty");
        }
        this.id = builder.id;
        this.attributes = builder.attributes;
        this.type = builder.type;
    }

    /**
     * Creates builder to build {@link GetCacheResponseData}.
     *
     * @return created builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public GetCacheResponseAttributes getAttributes() {
        return attributes;
    }

    @Override
    public String getType() {
        return type;
    }

    /**
     * Builder to build {@link GetCacheResponseData}.
     */
    public static final class Builder {
        private String id;
        private GetCacheResponseAttributes attributes;
        private String type;

        private Builder() {
        }

        /**
         * Sets The Id.
         *
         * @param id The Id value.
         * @return builder.
         */
        public Builder withId(String id) {
            if (isBlank(id)) {
                throw new IllegalArgumentException("id field can't be null or empty");
            }

            this.id = id;
            return this;
        }

        /**
         * Sets The GetCacheResponseAttributes.
         *
         * @param attributes The GetCacheResponseAttributes value.
         * @return builder.
         */
        public Builder withAttributes(GetCacheResponseAttributes attributes) {
            if (isNull(attributes)) {
                throw new IllegalArgumentException("attributes field can't be null");
            }

            this.attributes = attributes;
            return this;
        }

        /**
         * Sets The Type.
         *
         * @param type The Type value.
         * @return builder.
         */
        public Builder withType(String type) {
            if (isBlank(type)) {
                throw new IllegalArgumentException("type field can't be null or empty");
            }

            this.type = type;
            return this;
        }

        public GetCacheResponseData build() {
            return new GetCacheResponseData(this);
        }
    }
}
