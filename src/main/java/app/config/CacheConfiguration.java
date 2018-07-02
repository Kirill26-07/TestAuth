package app.config;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class CacheConfiguration {

    @Bean
    public EhCacheCacheManager ehCacheCacheManager() {
        EhCacheCacheManager cacheManager = new EhCacheCacheManager();
        cacheManager.setCacheManager(ehCacheManagerFactory().getObject());
        cacheManager.setTransactionAware(true);
        return cacheManager;
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactory() {
        EhCacheManagerFactoryBean cacheManagerFactory = new EhCacheManagerFactoryBean();
        cacheManagerFactory.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cacheManagerFactory.setShared(true);
        return cacheManagerFactory;
    }
}
