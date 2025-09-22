package com.bajaj.assessment.runner;

import com.bajaj.assessment.service.WebhookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartupRunner.class);

    @Autowired
    private WebhookService webhookService;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Application started - executing webhook flow...");
        webhookService.processWebhookFlow();
    }
}