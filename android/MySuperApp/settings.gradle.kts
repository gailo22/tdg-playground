plugins {
    // https://plugins.gradle.org/plugin/com.gradle.enterprise
    id("com.gradle.enterprise").version("3.4")
}

gradleEnterprise {
    buildScan {
        // Accept the license agreement for com.gradle.build-scan plugin
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlways()
    }
}

include(
    ":app",
    ":common:resources",
    ":common:cache",
    ":common:network",
    ":common:views",
    ":navigation",
    ":libs:usermanager",
    ":features:home",
    ":features:onboarding",
    ":features:profile"
)