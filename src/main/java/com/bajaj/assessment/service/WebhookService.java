package com.bajaj.assessment.service;

import com.bajaj.assessment.dto.SolutionRequest;
import com.bajaj.assessment.dto.WebhookRequest;
import com.bajaj.assessment.dto.WebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {

    private static final Logger logger = LoggerFactory.getLogger(WebhookService.class);

    private static final String GENERATE_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SqlSolverService sqlSolverService;

    public void processWebhookFlow() {
        try {
            logger.info("Starting webhook flow...");

            // Step 1: Generate webhook
            WebhookResponse webhookResponse = generateWebhook();
            logger.info("Webhook generated successfully: {}", webhookResponse.getWebhook());

            // Step 2: Solve SQL problem based on regNo
            String regNo = "0101IT221076";
            String sqlSolution = sqlSolverService.solveSqlProblem(regNo);
            logger.info("SQL solution generated: {}", sqlSolution);

            // Step 3: Submit solution
            submitSolution(webhookResponse.getWebhook(), webhookResponse.getAccessToken(), sqlSolution);
            logger.info("Solution submitted successfully");

        } catch (Exception e) {
            logger.error("Error in webhook flow: ", e);
            throw new RuntimeException("Failed to complete webhook flow", e);
        }
    }

    private WebhookResponse generateWebhook() {
        WebhookRequest request = new WebhookRequest(
                "Vivekraj Singh Sisodiya",
                "0101IT221076",
                "vivekuit344@gmail.com"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<WebhookResponse> response = restTemplate.exchange(
                GENERATE_WEBHOOK_URL,
                HttpMethod.POST,
                entity,
                WebhookResponse.class
        );

        return response.getBody();
    }

    private void submitSolution(String webhookUrl, String accessToken, String sqlQuery) {
        SolutionRequest solutionRequest = new SolutionRequest(sqlQuery);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);

        HttpEntity<SolutionRequest> entity = new HttpEntity<>(solutionRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                webhookUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        logger.info("Submission response: {}", response.getBody());
    }
}
