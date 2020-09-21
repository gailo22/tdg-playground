package com.gailo22.oauth2.oauth2testapp.login.web.controllers;

import java.util.Map;

import com.gailo22.oauth2.oauth2testapp.login.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private UserService service;

    public UserRestController(UserService service) {
        this.service = service;
    }

    @GetMapping("/oidc-principal")
    public OidcUser getOidcUserPrincipal(@AuthenticationPrincipal OidcUser principal) {
        return principal;
    }

    @GetMapping("/oidc-claims")
    public Map<String, Object> getClaimsFromBean() {
        return service.getUserClaims();
    }

    @GetMapping("/oidc-userinfo")
    public OidcUserInfo getUserInfoBean() {
        return service.getUserInfo();
    }

    @GetMapping("/oidc-idtoken")
    public OidcIdToken getIdToken() {
        return service.getIdToken();
    }
}
