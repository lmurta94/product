package com.lucasti.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    @Autowired
    CacheManager cacheManager;


    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.DAYS)
    public void evitCacheBy(String name){
        System.out.println("Schedule");
        Objects.requireNonNull(cacheManager.getCache(name)).clear();
    }
}
