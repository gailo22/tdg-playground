package com.gailo22.oauth2.resourceserver

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import java.lang.Exception


@Configuration
class ResourceServerSecurityConfig: WebSecurityConfigurerAdapter() {
    @Value("\${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    var jwkSetUri: String? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http
            .authorizeRequests(
                Customizer { authorizeRequests ->
                    authorizeRequests
                        .antMatchers(HttpMethod.GET, "/message/**").hasAuthority("SCOPE_message:read")
                        .antMatchers(HttpMethod.POST, "/message/**").hasAuthority("SCOPE_message:write")
                        .anyRequest().authenticated()
                }
            )
            .oauth2ResourceServer { obj: OAuth2ResourceServerConfigurer<HttpSecurity?> -> obj.jwt() }
        // @formatter:on
    }

    @Bean
    fun jwtDecoder(): JwtDecoder? {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build()
    }
}