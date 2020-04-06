package com.it.config.listener;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Slf4j
public class CustomCacheEventLogger implements CacheEventListener<Object, Object> {

    /**
     * Configures listener
     *
     * @param cacheEvent
     */
    @Override
    public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
        log.info("Cache event = {}, Key = {}, Old value = {}, New value = {}", cacheEvent.getType(), cacheEvent.getKey(),
                cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }
}
