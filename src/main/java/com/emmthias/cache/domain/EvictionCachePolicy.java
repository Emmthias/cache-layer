package com.emmthias.cache.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "EvictionCachePolicy", description = "Eviction cache indicates what to do when the cache runs " +
        "out of slots. The following options are:\n" +
        "\t○ OLDEST_FIRST: If there are no slots available the cache will evict the oldest active object and store " +
        "        the new object in its place\n" +
        "\t○ NEWEST_FIRST: If there are no slots available the cache will evict the newest active object first and " +
        "        store the new object in its place\n" +
        "\t○ REJECT: When the cache runs out of storage it just reject the store request")
public enum EvictionCachePolicy {
    OLDER_FIRST,
    NEWEST_FIRST,
    REJECT,
}
