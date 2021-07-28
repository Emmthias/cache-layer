package com.emmthias.cache.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

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
public class CachePreference {

    @Value(SLOT_NUMBER_PROPERTY)
    Integer SLOT_NUMBER_VALUE;

    @Value(TIME_TO_LIVE_PROPERTY)
    Integer TIME_TO_LIVE_VALUE;

    @Value(EVICTION_CACHE_PROPERTY)
    EvictionCachePolicy EVICTION_CACHE_VALUE;

    @JsonProperty("slotNumber")
    @ApiModelProperty(required = true, value = " Maximum number of objects to be stored simultaneously in the " +
            "server’s memory, default value 10,000." +
            "If the server runs out of slots it will behave according to the Eviction cache Policy setting")
    Integer slotNumber;

    @JsonProperty("timeToLive")
    @ApiModelProperty(required = true, value = " Object’s default time-to-live value in seconds if no TTL is " +
            "specified as part of a write request. If TTL is set to 0 that means store indefinitely (until an " +
            "explicit DELETE request)")
    Integer timeToLive;

    @JsonProperty("evictionCachePolicy")
    @ApiModelProperty(required = true, value = " This indicates what to do when the cache runs out of slots.")
    EvictionCachePolicy evictionCachePolicy;

    public CachePreference(Builder builder) {
        this.slotNumber = isNull(builder.build().getSlotNumber()) ? SLOT_NUMBER_VALUE : builder.slotNumber;
        this.timeToLive = isNull(builder.build().getTimeToLive()) ? TIME_TO_LIVE_VALUE : builder.timeToLive;
        this.evictionCachePolicy = isNull(builder.build().getEvictionCachePolicy()) ? EVICTION_CACHE_VALUE :
                builder.evictionCachePolicy;
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

    public static Builder builder() {
        return new Builder();
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

        //TODO add catch exception
        public Builder withEvictionCachePolicy(String evictionCachePolicy) {
            this.evictionCachePolicy = EvictionCachePolicy.valueOf(evictionCachePolicy);
            return this;
        }

        public CachePreference build() {
            return new CachePreference(this);
        }
    }
}
