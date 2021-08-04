package com.emmthias.cache.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.emmthias.cache.constants.Constants.SUGGESTED_IDS;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "CacheObject", description = "This object hold the cache data divided in the following " +
        "properties:\n" +
        "* {value} the data.\n" +
        "* {key} The unique value to identify the object.\n")
@JsonPropertyOrder({"key", "value"})
@Component
public class CacheObject implements ICacheObject<String, JSONObject> {

    private static String[] suggestedIds;
    @JsonProperty("value")
    @ApiModelProperty(required = true, value = " Data to hold the information needed.")
    JSONObject value;
    @JsonProperty("key")
    @ApiModelProperty(required = true, value = " key to identify the data.")
    String key;

    public CacheObject(JSONObject value) {
        this();
        this.setValue(value);
    }

    private CacheObject() {
    }

    private String calculateKey(Map<String, Object> currentJsonValue) {
        String key = null;

        if (nonNull(suggestedIds)) {
            for (String suggestedIdKey : suggestedIds) {
                if (currentJsonValue.containsKey(suggestedIdKey)) {
                    key = currentJsonValue.get(suggestedIdKey).toString();
                    break;
                }
            }
        }

        return isNull(key) ? String.valueOf(currentJsonValue.hashCode()) : key;
    }

    @Autowired
    private void setSuggestedIds(@Value(SUGGESTED_IDS) String[] suggestedIds) {
        CacheObject.suggestedIds = suggestedIds;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public JSONObject getValue() {
        return this.value;
    }

    @Override
    public JSONObject setValue(JSONObject jsonObject) {
        this.value = jsonObject;
        this.key = calculateKey(jsonObject.toMap());

        return value;
    }
}
