package com.javarush.bashkirceva.springproject.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class CustomMetricsService {

    private final Counter failedLoginCounter;

    public CustomMetricsService(MeterRegistry registry) {
        this.failedLoginCounter = registry.counter("auth.login.failed", "type", "failed_login");
    }

    public void incrementFailedLoginCounter() {
        failedLoginCounter.increment();
    }
}
