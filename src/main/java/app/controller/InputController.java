package app.controller;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class InputController {

    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void inputSignal(@RequestParam(value = "name") String name) {
        Element element = new Element(name, name + 123);
        Cache cache = ehCacheCacheManager.getCacheManager().getCache("tokenCache");
        cache.put(element);
        System.out.println(cache.get(name));
    }

}
