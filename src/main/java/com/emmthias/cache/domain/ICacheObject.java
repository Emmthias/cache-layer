package com.emmthias.cache.domain;

import java.io.Serializable;
import java.util.Map;

public interface ICacheObject<K, V> extends Map.Entry<K, V>, Serializable {

}
