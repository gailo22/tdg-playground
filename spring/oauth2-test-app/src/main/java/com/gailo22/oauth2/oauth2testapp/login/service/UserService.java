package com.gailo22.oauth2.oauth2testapp.login.service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public Map<String, Object> getUserClaims() {
        return getOidcUser()
                .map(OidcUser::getClaims)
                .orElse(Collections.emptyMap());
    }

    public OidcUserInfo getUserInfo() {
        return getOidcUser()
                .map(OidcUser::getUserInfo)
                .orElse(null);
    }

    public OidcIdToken getIdToken() {
        return getOidcUser()
                .map(OidcUser::getIdToken)
                .orElse(null);
    }


    private Optional<OidcUser> getOidcUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication.getPrincipal() instanceof OidcUser) {
            return Optional.of(((OidcUser) authentication.getPrincipal()));
        }
        return Optional.empty();
    }
}
