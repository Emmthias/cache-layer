package com.emmthias.cache.constants;

public class Constants {
    public final static String POM_VERSION = "${cache.version:No Information Available}";
    public final static String CACHE_SERVICE_ENDPOINT = "cache-service/api/";
    public final static String REQUEST_TYPE_GET = "GET";
    public final static String REQUEST_TYPE_POST = "POST";
    public final static String REQUEST_TYPE_PATCH = "PATCH";
    public final static String REQUEST_TYPE_PUT = "PUT";
    public final static String REQUEST_TYPE_DELETE = "DELETE";
    public static final String KEY = "key";
    public static final String CACHE_BUCKET_URL = "bucket";
    public static final String CACHE_BUCKET_BY_KEY_URL = CACHE_BUCKET_URL + "/{" + KEY + "}";
    public static final String CACHE_OBJECT_URL = "/{" + KEY + "}/object";
    public final static String CACHE_SERVICE_BUCKET_ENDPOINT = CACHE_SERVICE_ENDPOINT + CACHE_BUCKET_URL;
    public static final Integer SLOT_NUMBER_DEFAULT_VALUE = 10000;
    public static final Integer TIME_TO_LIVE_DEFAULT_VALUE = 3600;
    public static final String EVICTION_POLICY_DEFAULT_VALUE_NAME = "REJECT";
    public static final String SLOT_NUMBER_PROPERTY = "${cache-service.preference.slotNumbers:10000}";
    public static final String TIME_TO_LIVE_PROPERTY = "${cache-service.preference.timeToLive:3600}";
    public static final String EVICTION_CACHE_PROPERTY = "${cache-service.preference.evictionCachePolicy:REJECT}";
    public static final String SUGGESTED_IDS = "${cache.suggested-key-properties-ids-for-cache-object:null}";
    public static final String CACHE_PREFERENCE_SLOT_NUMBER = "slotNumber";
    public static final String CACHE_PREFERENCE_TTL = "timeToLive";
    public static final String CACHE_PREFERENCE_EVICTION_POLICY = "evictionCachePolicy";
    public static final String CACHE_RESOURCE_TYPE = "cache";
    public static String UNKNOWN_ERROR_MSG = "Unknown Error";
    public static String ALREADY_BUCKET_NAME_IN_USE_MSG = "Provided key '%s' was already in use";
    public static String KEY_NOT_FOUND_MSG = "Provided key '%s' was no found";
    public static int AVOID_DELAY_EXECUTION = 0;
    public static String REJECT_POLICY_WARN_MESSAGE = "REJECT POLICY Applied, it will insert only a subset of the " +
            "original request %d elements of a total %d";
    public static String REJECT_POLICY_MESSAGE = "REJECT POLICY Applied.";
    public static String NEWEST_POLICY_WARN_MESSAGE = "NEWEST POLICY applied for %d missing element number";
    public static String ELDEST_MECHANISM_APPLIED = "removeEldest Mechanism applied";
    public static String SCHEDULER_CLEAN_LOG_MESSAGE = "Clean instance with %d elements";
    public static String SCHEDULER_CONFIGURE_LOG_MESSAGE = "scheduleCleanElements configure every %d seconds";
    public static String SCHEDULER_NOT_CONFIGURE_LOG_MESSAGE = "scheduleCleanElements disable";
    public static String DELETED_BUCKET_MESSAGE = "The bucket '%s' was deleted successfully";
    public static String DELETED_BUCKET_OBJECT_MESSAGE = "The object '%s' was deleted successfully";
}
