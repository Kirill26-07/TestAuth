package app.auth;

import app.config.TokenSaltConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationToken {

    public static String getNewToken(final String userName, final String userPassword) {
        return DigestUtils.sha256Hex(userPassword + TokenSaltConfig.TOKEN_SALT + userName);
    }
}
