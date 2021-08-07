package com.emmthias.cache.utils;

import com.emmthias.cache.domain.CacheObject;
import com.emmthias.cache.domain.CachePreference;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.emmthias.cache.constants.Constants.EVICTION_POLICY_DEFAULT_VALUE_NAME;
import static com.emmthias.cache.constants.Constants.SLOT_NUMBER_DEFAULT_VALUE;
import static com.emmthias.cache.constants.Constants.TIME_TO_LIVE_DEFAULT_VALUE;

public class Constant {
    private final static CachePreference EXPECTED_DEFAULT_CACHE_PREFERENCE =
            CachePreference.builder()
                    .withSlotNumber(SLOT_NUMBER_DEFAULT_VALUE)
                    .withEvictionCachePolicy(EVICTION_POLICY_DEFAULT_VALUE_NAME)
                    .withTimeToLive(TIME_TO_LIVE_DEFAULT_VALUE).build();

    private final static JSONObject FIRST_ELEMENT = new JSONObject().put("id", 1).put("name", "fx");
    private final static JSONObject SECOND_ELEMENT = new JSONObject().put("name", "dos");
    private final static JSONObject THIRD_ELEMENT = new JSONObject().put("version", "1.2");
    private final static JSONObject FOUR_ELEMENT = new JSONObject().put("id", 4);
    private final static JSONObject FIVE_ELEMENT = new JSONObject().put("name", "cinco");

    private final static JSONObject[] ELEMENTS = new JSONObject[]{FIRST_ELEMENT, SECOND_ELEMENT, THIRD_ELEMENT,
            FOUR_ELEMENT, FIVE_ELEMENT};
    private final static List<CacheObject> EXPECTED_OBJECT =
            Stream.of(ELEMENTS).map(CacheObject::new).collect(Collectors.toList());

    public static CachePreference getEXPECTED_DEFAULT_CACHE_PREFERENCE() {
        return EXPECTED_DEFAULT_CACHE_PREFERENCE;
    }

    public static JSONObject getFIRST_ELEMENT() {
        return FIRST_ELEMENT;
    }

    public static JSONObject[] getELEMENTS() {
        return ELEMENTS;
    }

    public static List<CacheObject> getEXPECTED_OBJECT() {
        return EXPECTED_OBJECT;
    }
}
