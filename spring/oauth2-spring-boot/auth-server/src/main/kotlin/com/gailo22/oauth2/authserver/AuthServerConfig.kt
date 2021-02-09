package com.gailo22.oauth2.authserver

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest


@EnableAuthorizationServer
@Configuration
class AuthServerConfig: AuthorizationServerConfigurerAdapter() {

    var authenticationManager: AuthenticationManager? = null
    var keyPair: KeyPair? = null
    var jwtEnabled = false

    @Throws(Exception::class)
    fun AuthorizationServerConfiguration(
        authenticationConfiguration: AuthenticationConfiguration,
        keyPair: KeyPair?,
        @Value("\${security.oauth2.authorizationserver.jwt.enabled:true}") jwtEnabled: Boolean
    ) {
        authenticationManager = authenticationConfiguration.authenticationManager
        this.keyPair = keyPair
        this.jwtEnabled = jwtEnabled
    }

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        // @formatter:off
        clients.inMemory()
            .withClient("reader")
            .authorizedGrantTypes("password")
            .secret("{noop}secret")
            .scopes("message:read")
            .accessTokenValiditySeconds(600000000)
            .and()
            .withClient("writer")
            .authorizedGrantTypes("password")
            .secret("{noop}secret")
            .scopes("message:write")
            .accessTokenValiditySeconds(600000000)
            .and()
            .withClient("noscopes")
            .authorizedGrantTypes("password")
            .secret("{noop}secret")
            .scopes("none")
            .accessTokenValiditySeconds(600000000)
        // @formatter:on
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        // @formatter:off
        endpoints
            .authenticationManager(authenticationManager)
            .tokenStore(tokenStore())
        if (jwtEnabled) {
            endpoints
                .accessTokenConverter(accessTokenConverter())
        }
        // @formatter:on
    }

    @Bean
    fun tokenStore(): TokenStore? {
        return if (jwtEnabled) {
            JwtTokenStore(accessTokenConverter())
        } else {
            InMemoryTokenStore()
        }
    }

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter? {
        val converter = JwtAccessTokenConverter()
        converter.setKeyPair(keyPair)
        val accessTokenConverter = DefaultAccessTokenConverter()
        accessTokenConverter.setUserTokenConverter(SubjectAttributeUserTokenConverter())
        converter.accessTokenConverter = accessTokenConverter
        return converter
    }
}

@Configuration
internal class UserConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .mvcMatchers("/.well-known/jwks.json").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic()
            .and()
            .csrf()
            .ignoringRequestMatchers(RequestMatcher { request: HttpServletRequest -> "/introspect" == request.requestURI })
    }

    @Bean
    public override fun userDetailsService(): UserDetailsService {
        return InMemoryUserDetailsManager(
            User.withDefaultPasswordEncoder()
                .username("subject")
                .password("password")
                .roles("USER")
                .build()
        )
    }
}

@FrameworkEndpoint
class IntrospectEndpoint(var tokenStore: TokenStore) {
    @PostMapping("/introspect")
    @ResponseBody
    fun introspect(@RequestParam("token") token: String?): MutableMap<String, Any> {
        val accessToken = tokenStore.readAccessToken(token)
        val attributes: MutableMap<String, Any> = HashMap()
        if (accessToken == null || accessToken.isExpired) {
            attributes["active"] = false
            return attributes
        }
        val authentication = tokenStore.readAuthentication(token)
        attributes["active"] = true
        attributes["exp"] = accessToken.expiration.time
        attributes["scope"] = accessToken.scope.stream().collect(Collectors.joining(" "))
        attributes["sub"] = authentication.name
        return attributes
    }
}

@FrameworkEndpoint
class JwkSetEndpoint(var keyPair: KeyPair) {
    @get:ResponseBody
    @get:GetMapping("/.well-known/jwks.json")
    val key: MutableMap<String, Any>?
        get() {
            val publicKey: RSAPublicKey = keyPair.public as RSAPublicKey
            val key: RSAKey = RSAKey.Builder(publicKey).build()
            return JWKSet(key).toJSONObject()
        }

}

@Configuration
class KeyConfig {
    @Bean
    fun keyPair(): KeyPair {
        return try {
            val privateExponent =
                "3851612021791312596791631935569878540203393691253311342052463788814433805390794604753109719790052408607029530149004451377846406736413270923596916756321977922303381344613407820854322190592787335193581632323728135479679928871596911841005827348430783250026013354350760878678723915119966019947072651782000702927096735228356171563532131162414366310012554312756036441054404004920678199077822575051043273088621405687950081861819700809912238863867947415641838115425624808671834312114785499017269379478439158796130804789241476050832773822038351367878951389438751088021113551495469440016698505614123035099067172660197922333993"
            val modulus =
                "18044398961479537755088511127417480155072543594514852056908450877656126120801808993616738273349107491806340290040410660515399239279742407357192875363433659810851147557504389760192273458065587503508596714389889971758652047927503525007076910925306186421971180013159326306810174367375596043267660331677530921991343349336096643043840224352451615452251387611820750171352353189973315443889352557807329336576421211370350554195530374360110583327093711721857129170040527236951522127488980970085401773781530555922385755722534685479501240842392531455355164896023070459024737908929308707435474197069199421373363801477026083786683"
            val exponent = "65537"
            val publicSpec = RSAPublicKeySpec(BigInteger(modulus), BigInteger(exponent))
            val privateSpec = RSAPrivateKeySpec(BigInteger(modulus), BigInteger(privateExponent))
            val factory: KeyFactory = KeyFactory.getInstance("RSA")
            KeyPair(factory.generatePublic(publicSpec), factory.generatePrivate(privateSpec))
        } catch (e: Exception) {
            throw IllegalArgumentException(e)
        }
    }
}

class SubjectAttributeUserTokenConverter : DefaultUserAuthenticationConverter() {
    override fun convertUserAuthentication(authentication: Authentication): MutableMap<String, Any> {
        val response: MutableMap<String, Any> = LinkedHashMap()
        response["sub"] = authentication.getName()
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response[AUTHORITIES] = AuthorityUtils.authorityListToSet(authentication.getAuthorities())
        }
        return response
    }
}