package com.gailo22.oauth2testapp2.resolutions;

import org.springframework.security.core.annotation.CurrentSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@CurrentSecurityContext(expression="authentication.name")
public @interface CurrentUsername {
}
