package com.spring.cachesync.cache;

public interface ICache {
	 void add(String key, Object value, long periodInMillis);

	    void remove(String key);

	    Object get(String key);

	    void clear();

	    long size();
}
