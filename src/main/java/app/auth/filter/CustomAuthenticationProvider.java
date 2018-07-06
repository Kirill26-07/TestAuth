package app.auth.filter;

import app.auth.AuthorizationToken;
import app.controller.CachingService;
import app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CachingService tokenCachingService;

    @Autowired
    private AuthorizationToken authorizationToken;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = auth.getName();
        String password = getHashPassword(auth.getCredentials().toString());
        String token = authorizationToken.getNewToken(username, password);
        log.info(token);

        if (tokenCachingService.isAuthenticate(token)) {
            log.info("Authenticated!");
            throw new BadCredentialsException("External system authentication failed");
        } else if (!userRepository.findAllByPassword(password).isEmpty()) {
            log.info("Find user in database");
            tokenCachingService.addToken(token);
            throw new BadCredentialsException("External system authentication failed");
        } else {
            throw new BadCredentialsException("External system authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }

    private String getHashPassword(String password) {
       return Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
    }

}