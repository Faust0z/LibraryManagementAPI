package com.faust0z.BookLibraryAPI.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class StartupLogger implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("BookLibraryAPI has successfully started.");
        log.info("Active Profiles: {}", Arrays.toString(event.getApplicationContext().getEnvironment().getActiveProfiles()));
    }
}