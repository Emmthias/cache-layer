package com.emmthias.cache.common.response.success.impl;

import com.emmthias.cache.common.response.success.IApiSuccessResponsePayload;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents the GetCacheResponsePayload model.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"codes"})
@JsonDeserialize(builder = GetCacheResponsePayload.Builder.class)
@ApiModel("GetCacheResponsePayload")
public class GetCacheResponsePayload implements IApiSuccessResponsePayload {
    private static final long serialVersionUID = 7162816226224489093L;

    @ApiModelProperty(required = false, allowEmptyValue = true, value = "The list of elements.")
    @JsonProperty("elements")
    private final Collection<Map<String, ?>> elements;

    @ApiModelProperty(required = false, allowEmptyValue = true, value = "The list of elements.")
    @JsonProperty("element")
    private final Map<String, ?> element;

    private GetCacheResponsePayload(Builder builder) {
        this.elements = builder.elements;
        this.element = builder.element;
    }

    /**
     * Creates builder to build {@link GetCacheResponsePayload}.
     *
     * @return created builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public Collection<Map<String, ?>> getElements() {
        return elements;
    }

    public Map<String, ?> getElement() {
        return element;
    }

    /**
     * Builder to build {@link GetCacheResponsePayload}.
     */
    public static final class Builder {
        private Collection<Map<String, ?>> elements;
        private Map<String, ?> element;

        private Builder() {
        }

        /**
         * Sets the elements.
         *
         * @param elements The jsonObjects list
         * @return builder
         */
        public Builder withElements(Collection<JSONObject> elements) {
            this.elements = elements.stream().map(e -> e.toMap()).collect(Collectors.toList());
            return this;
        }


        /**
         * Sets the element.
         *
         * @param element The jsonObjects list
         * @return builder
         */
        public Builder withElement(JSONObject element) {
            this.element = element.toMap();
            return this;
        }

        /**
         * Builds and validates the GetCacheResponsePayload.
         *
         * @return an instance of GetCacheResponsePayload
         */
        public GetCacheResponsePayload build() {
            return new GetCacheResponsePayload((this));
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"elements\":"  + elements +
                ", \"element\": " + element +
                '}';
    }
}
