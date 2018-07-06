package app.controller;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Service
public class CachingService {

    @Autowired
    private CacheManager tokenCacheManager;

    private Cache tokenCache;

    @PostConstruct
    private void init() {
        tokenCache = tokenCacheManager.getCache("tokenCache");
    }

    public void addToken(final String token){
        Element cacheElement = new Element(token, token);
        tokenCache.put(cacheElement);
    }

    public boolean isAuthenticate(final String authorizationToken) {
        tokenCache.evictExpiredElements();
        return tokenCache.isKeyInCache(authorizationToken);
    }

    @RestController
    public class CacheController {

        @RequestMapping(value = "/api/cache/clean")
        public void clean() {
            tokenCache.removeAll();
        }

    }
}
