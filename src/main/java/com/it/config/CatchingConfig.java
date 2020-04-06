package com.it.config;

import com.it.config.listener.CustomCacheEventLogger;
import com.it.config.utils.CacheName;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.event.CacheEventListener;
import org.ehcache.event.EventType;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;

import javax.cache.Caching;
import java.util.HashMap;
import java.util.Map;

@EnableCaching
public class CatchingConfig {

    @Value("${encache.heap.size}")
    private String heapSize;

    @Bean
    public CacheManager cacheManager() {
        EhcacheCachingProvider provider = (EhcacheCachingProvider) Caching.getCachingProvider();

        DefaultConfiguration configuration = new DefaultConfiguration(createCaches(getCacheConfiguration()), provider.getDefaultClassLoader());
        JCacheCacheManager cacheManager = new JCacheCacheManager(provider.getCacheManager(provider.getDefaultURI(), configuration));
        cacheManager.setTransactionAware(true);
        return cacheManager;
    }

    /**
     * Creates Map of caches
     *
     * @param cacheConfiguration - {@link CacheConfiguration}
     * @return - {@link Map} of caches
     */
    private Map<String, CacheConfiguration<?, ?>> createCaches(CacheConfiguration<?, ?> cacheConfiguration) {
        Map<String, CacheConfiguration<?, ?>> caches = new HashMap<>();

        caches.put(CacheName.USER, cacheConfiguration);
        caches.put(CacheName.ROLE, cacheConfiguration);
        caches.put(CacheName.HOTEL, cacheConfiguration);
        caches.put(CacheName.HOTEL_ROOM, cacheConfiguration);
        caches.put(CacheName.HOTEL_ROOM_HOTEL, cacheConfiguration);
        caches.put(CacheName.HOTEL_ROOM_PRICE, cacheConfiguration);
        caches.put(CacheName.HOTEL_ADDRESS, cacheConfiguration);
        caches.put(CacheName.INSURANCE, cacheConfiguration);
        caches.put(CacheName.CUSTOMER, cacheConfiguration);
        caches.put(CacheName.ORDER_LIST, cacheConfiguration);

        return caches;
    }

    /**
     * Creates configuration for heap cache, adds {@link CacheEventListener}
     *
     * @return - {@link CacheConfiguration}
     */
    private CacheConfiguration<?, ?> getCacheConfiguration() {
        ResourcePoolsBuilder resourcePoolsBuilder = ResourcePoolsBuilder.heap(Long.parseLong(heapSize));

        CacheConfigurationBuilder<Object, Object> newCacheConfigurationBuilder = CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class, resourcePoolsBuilder).withService(cacheEventListener(new CustomCacheEventLogger()));

        return newCacheConfigurationBuilder.build();
    }

    /**
     * Creates custom configuration for {@link CacheEventListener}
     *
     * @param cacheEventListener - custom {@link CacheEventListener}
     * @return - custom {@link CacheEventListenerConfigurationBuilder}
     */
    private CacheEventListenerConfigurationBuilder cacheEventListener(CacheEventListener cacheEventListener) {

        return CacheEventListenerConfigurationBuilder
                .newEventListenerConfiguration(cacheEventListener, EventType.CREATED, EventType.EVICTED, EventType.EXPIRED, EventType.REMOVED, EventType.UPDATED)
                .unordered().asynchronous();
    }
}
