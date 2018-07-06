package app.auth;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.store.FifoPolicy;
import net.sf.ehcache.store.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public void clean() {
        tokenCache.removeAll();
    }

}
