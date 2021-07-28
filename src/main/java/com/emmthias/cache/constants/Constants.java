package com.emmthias.cache.constants;

public class Constants {
    public final static String POM_VERSION = "${cache.version:No Information Available}";
    public final static String CACHE_SERVICES_ENDPOINT = "cache-service/api/";
    public final static String REQUEST_TYPE_GET = "GET";
    public final static String REQUEST_TYPE_POST = "POST";
    public final static String REQUEST_TYPE_PATCH = "PATCH";
    public final static String REQUEST_TYPE_DELETE = "DELETE";
    public static final String KEY = "key";
    public static final String CACHE_URL = "object/{" + KEY + "}";

    public static final String SLOT_NUMBER_PROPERTY = "${cache-service.preference.slotNumbers:10000}";
    public static final String TIME_TO_LIVE_PROPERTY = "${cache-service.preference.timeToLive:3600}";
    public static final String EVICTION_CACHE_PROPERTY = "${cache-service.preference.evictionCachePolicy:REJECT}";
}
