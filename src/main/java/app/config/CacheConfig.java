package app.config;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class CacheConfig {

    public CacheConfiguration cacheConfig(){
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName("tokenCache");
        cacheConfiguration.setTimeToLiveSeconds(60);
        cacheConfiguration.setTimeToIdleSeconds(0);
        cacheConfiguration.overflowToOffHeap(false);
        cacheConfiguration.memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.CLOCK);
        return cacheConfiguration;
    }
}
