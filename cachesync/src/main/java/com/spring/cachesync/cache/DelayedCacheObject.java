package com.spring.cachesync.cache;
import java.lang.ref.SoftReference;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Description: Cached objects with key and expiration time
 * @Author laoxu
 * @Date 2019/7/27 15:28
 **/
public class DelayedCacheObject implements Delayed {
    private final String key;
    private final SoftReference<Object> reference;
    private final long expiryTime;

    public DelayedCacheObject(String key, SoftReference<Object> reference, long expiryTime) {
        this.key = key;
        this.reference = reference;
        this.expiryTime = expiryTime;
    }

    public String getKey() {
        return key;
    }

    public SoftReference<Object> getReference() {
        return reference;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(expiryTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(expiryTime, ((DelayedCacheObject) o).expiryTime);
    }
}