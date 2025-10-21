package pl.devqa.javaseleniumhub.config;

import java.time.Duration;

public final class AppConfig {
    private AppConfig() {}

    public static final String BASE_URL = "https://automationexercise.com";
    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
}
