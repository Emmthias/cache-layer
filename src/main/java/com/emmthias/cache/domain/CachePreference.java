package com.emmthias.cache.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.emmthias.cache.constants.Constants.CACHE_PREFERENCE_EVICTION_POLICY;
import static com.emmthias.cache.constants.Constants.CACHE_PREFERENCE_SLOT_NUMBER;
import static com.emmthias.cache.constants.Constants.CACHE_PREFERENCE_TTL;
import static com.emmthias.cache.constants.Constants.EVICTION_CACHE_PROPERTY;
import static com.emmthias.cache.constants.Constants.SLOT_NUMBER_PROPERTY;
import static com.emmthias.cache.constants.Constants.TIME_TO_LIVE_PROPERTY;
import static java.util.Objects.isNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "CachePreference", description = "Cache preference settings allow to configure the behavior of the " +
        "cache Object.\n" +
        "The precedence setting values is: builder value, property value, default value.\n" +
        "properties configuration prefix value wil be cache-service.preference:\n" +
        "\t* cache-service.preference.slotNumbers: `validValue`" +
        "\t* cache-service.preference.timeToLive: `validValue`" +
        "\t* cache-service.preference.evictionCachePolicy: `validValue`" +
        "or the following values:\n" +
        "\t* slotNumbers = 10,000\n" +
        "\t* timeToLive: 3,600 seconds\n" +
        "\t* evictionCachePolicy: REJECT")
@JsonPropertyOrder({"slotNumber, timeToLive", "evictionCachePolicy"})
@JsonDeserialize(builder = CachePreference.Builder.class)
@Component
public class CachePreference {

    @JsonIgnore
    static Integer SLOT_NUMBER_VALUE;
    @JsonIgnore
    static Integer TIME_TO_LIVE_VALUE;
    @JsonIgnore
    static EvictionCachePolicy EVICTION_CACHE_VALUE;

    @JsonProperty(CACHE_PREFERENCE_SLOT_NUMBER)
    @ApiModelProperty(required = true, value = " Maximum number of objects to be stored simultaneously in the " +
            "server’s memory, default value 10,000." +
            "If the server runs out of slots it will behave according to the Eviction cache Policy setting")
    Integer slotNumber;

    @JsonProperty(CACHE_PREFERENCE_TTL)
    @ApiModelProperty(required = true, value = " Object’s default time-to-live value in seconds if no TTL is " +
            "specified as part of a write request. If TTL is set to 0 that means store indefinitely (until an " +
            "explicit DELETE request)")
    Integer timeToLive;

    @JsonProperty(CACHE_PREFERENCE_EVICTION_POLICY)
    @ApiModelProperty(required = true, value = " This indicates what to do when the cache runs out of slots.")
    EvictionCachePolicy evictionCachePolicy;

    private CachePreference() {
    }

    public CachePreference(Builder builder) {
        this.slotNumber = isNull(builder.getSlotNumber()) ? SLOT_NUMBER_VALUE : builder.slotNumber;
        this.timeToLive = isNull(builder.getTimeToLive()) ? TIME_TO_LIVE_VALUE : builder.timeToLive;
        this.evictionCachePolicy = isNull(builder.getEvictionCachePolicy()) ? EVICTION_CACHE_VALUE :
                builder.evictionCachePolicy;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getSlotNumber() {
        return slotNumber;
    }

    public Integer getTimeToLive() {
        return timeToLive;
    }

    public EvictionCachePolicy getEvictionCachePolicy() {
        return evictionCachePolicy;
    }

    @Autowired
    @JsonIgnore
    private void setSlotNumberValue(@Value(SLOT_NUMBER_PROPERTY) Integer slotNumberValue) {
        SLOT_NUMBER_VALUE = slotNumberValue;
    }

    @Autowired
    @JsonIgnore
    private void setTimeToLiveValue(@Value(TIME_TO_LIVE_PROPERTY) Integer timeToLiveValue) {
        TIME_TO_LIVE_VALUE = timeToLiveValue;
    }

    @Autowired
    @JsonIgnore
    private void setEvictionCacheValue(@Value(EVICTION_CACHE_PROPERTY) String evictionCacheValue) {
        EVICTION_CACHE_VALUE = EvictionCachePolicy.valueOf(evictionCacheValue.toUpperCase());
    }

    public static final class Builder {
        private Integer slotNumber;
        private Integer timeToLive;
        private EvictionCachePolicy evictionCachePolicy;

        private Builder() {
        }

        public Builder withSlotNumber(Integer slotNumber) {
            this.slotNumber = slotNumber;
            return this;
        }

        public Builder withTimeToLive(Integer timeToLive) {
            this.timeToLive = timeToLive;
            return this;
        }

        public Builder withEvictionCachePolicy(String evictionCachePolicy) {
            this.evictionCachePolicy = EvictionCachePolicy.valueOf(evictionCachePolicy);
            return this;
        }

        public Integer getSlotNumber() {
            return slotNumber;
        }

        public Integer getTimeToLive() {
            return timeToLive;
        }

        public EvictionCachePolicy getEvictionCachePolicy() {
            return evictionCachePolicy;
        }

        public CachePreference build() {
            return new CachePreference(this);
        }
    }
}
