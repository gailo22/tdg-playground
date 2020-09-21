package com.gailo22.oauth2.oauth2testapp.discovery;

import com.gailo22.oauth2.oauth2testapp.utils.YamlLoaderInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringOidcDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringOidcDiscoveryApplication.class);
        ApplicationContextInitializer<ConfigurableApplicationContext> yamlInitializer = new YamlLoaderInitializer("discovery-application.yml");
        application.addInitializers(yamlInitializer);
        application.run(args);
    }

}
