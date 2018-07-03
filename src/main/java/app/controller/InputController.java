package app.controller;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class InputController {

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void inputSignal(@RequestParam(value = "name") String name) {
        Cache cache = cacheManager.getCache("tokenCache");
        Element element = new Element(name, name + 123);
        cache.put(element);
        System.out.println(cache.get(name));
    }

}
