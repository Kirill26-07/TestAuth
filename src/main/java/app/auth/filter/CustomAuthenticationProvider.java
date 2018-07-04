package app.auth.filter;

import app.auth.AuthorizationToken;
import app.auth.CachingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CachingService tokenCachingService;

    @Autowired
    private AuthorizationToken authorizationToken;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        String token = authorizationToken.getNewToken(username, password);
        log.info(token);

        if (tokenCachingService.isAuthenticate(token)) {
            log.info("Token in the cache yet!");
            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
        } else if ("kirill".equals(username) && "1234".equals(password)) {
            tokenCachingService.addToken(token);
            log.info("Add new token!");
            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
        } else {
            throw new BadCredentialsException("External system authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }

}