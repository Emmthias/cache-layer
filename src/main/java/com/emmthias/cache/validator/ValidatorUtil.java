package com.emmthias.cache.validator;

import com.emmthias.cache.domain.CachePreference;

import static java.util.Objects.isNull;

public class ValidatorUtil {
    public static boolean isBlank(String value) {
        return (isNull(value) || value.trim().isBlank() || value.trim().isEmpty());
    }
}
