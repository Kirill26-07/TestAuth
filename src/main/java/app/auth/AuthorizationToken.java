package app.auth;

import app.config.TokenSaltConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationToken {

    public String getNewToken(final String userName, String userPassword) {
        return DigestUtils.sha256Hex(userPassword + TokenSaltConfig.TOKEN_SALT + userPassword);
    }
}
