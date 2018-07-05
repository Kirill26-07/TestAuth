package app.auth.filter;

import app.auth.AuthorizationToken;
import app.auth.CachingService;
import app.auth.entity.User;
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
        String password = auth.getCredentials().toString();
        String encodePass = Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));

        System.out.println(username);
        System.out.println(encodePass);
        String token = authorizationToken.getNewToken(username, password);
        log.info(token);

        if (tokenCachingService.isAuthenticate(token)) {
            log.info("Token in the cache yet!");
//            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
            throw new BadCredentialsException("External system authentication failed");
        } else if (userRepository.findAllByPassword(encodePass).isEmpty()) {
            log.info("Wrong password");
//            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
            throw new BadCredentialsException("External system authentication failed");
        } else if (!userRepository.findAllByPassword(encodePass).isEmpty()) {
            log.info("Find user in database");
            throw new BadCredentialsException("External system authentication failed");
        }
        else {
            throw new BadCredentialsException("External system authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }

}