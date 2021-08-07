package com.emmthias.cache.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Cache", description = "class holder for requests, following the cache preference builder")
@JsonPropertyOrder({"cachePreference, elements", "element"})
@JsonDeserialize(builder = Cache.Builder.class)
@Component
public class Cache {
    @JsonProperty("cachePreference")
    @ApiModelProperty(required = false, value = "cache preference that define the behavior of the cache structure, if" +
            " not set, it will take the values from properties, if not it will populated with default values.")
    CachePreference cachePreference;

    @JsonProperty("elements")
    @ApiModelProperty(required = false, value = "json elements to be store in cache structure.")
    List<Map<String, String>> elements;

    @JsonProperty("element")
    @ApiModelProperty(required = false, value = "json element to be store in cache structure")
    Map<String, String> element;

    private Cache() {
    }

    public Cache(Builder builder) {
        this.cachePreference = nonNull(builder.getCachePreference()) ? builder.getCachePreference() :
                CachePreference.builder().build();
        this.elements = builder.getElements();
        this.element = builder.getElement();
    }

    public static Builder builder() {
        return new Builder();
    }

    public CachePreference getCachePreference() {
        return cachePreference;
    }

    public List<Map<String, String>> getElements() {
        return elements;
    }

    public Map<String, String> getElement() {
        return element;
    }

    @Override
    public String toString() {
        return "{" +
                "\"cachePreference\": " + cachePreference +
                ", \"elements\": " + elements +
                ", \"element\": " + element +
                '}';
    }

    public static final class Builder {
        private CachePreference cachePreference;
        private List<Map<String, String>> elements;
        private Map<String, String> element;

        private Builder() {
        }

        public Builder withCachePreference(CachePreference cachePreference) {
            this.cachePreference = cachePreference;
            return this;
        }

        public Builder withElements(List<Map<String, String>> elements) {
            this.elements = elements;
            return this;
        }

        public Builder withElement(Map<String, String> element) {
            this.element = element;
            return this;
        }

        public CachePreference getCachePreference() {
            return cachePreference;
        }

        public List<Map<String, String>> getElements() {
            return elements;
        }

        public Map<String, String> getElement() {
            return element;
        }

        public Cache build() {
            return new Cache(this);
        }
    }
}
