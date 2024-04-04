package org.capstone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.capstone.Main;
import org.capstone.entity.WebexMessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebexService {
    public WebClient webClient;
    private final String BOT_ACCESS_TOKEN = "NDMzZWUwZjctOTY0MC00NGE3LTgyZjUtM2YyYmU3ZGZmNjI5NDgxMjFmNzYtYTgy_PF84_2092460f-fdc1-4ed7-bd66-d3bf39c66563";
    private final String LOGIN_MESSAGE = "Not implemented";
    private final String LEAVE_APPROVED_MESSAGE = "Not implemented";
    private final String PASSWORD_RESET_MESSAGE = "ALERT! Your password has been reset. If this was not you, please contact security. Unfortunately for you, our security department does not exist - this being a nonexistent company and all. Good luck!";
    @Autowired
    public WebexService(WebClient webClient){
        this.webClient = webClient;
    }


    public void callWebexAPI(String messageBody) {
        webClient.post()
                .uri("https://webexapis.com/v1/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + BOT_ACCESS_TOKEN)
                .bodyValue(messageBody)
                .retrieve()
                .toEntity(String.class)
                .subscribe(
                        responseEntity -> {
                            // Handle success response here
                            Main.logger.info("Status: " + responseEntity.getStatusCode().value());
                            Main.logger.info("Response Body : " + responseEntity.getBody());
                        },
                        error -> {
                            // Handle the error here
                            if (error instanceof WebClientResponseException ex) {
                                HttpStatusCode status = ex.getStatusCode();
                                Main.logger.warn("Error Status Code: " + status.value());

                            } else {
                                // Handle other types of errors
                                Main.logger.warn("An unexpected error occurred: " + error.getMessage());
                            }
                        });
    }

    // Send message with pre-defined text
    public void sendMessage(String email, WebexMessageType messageType){
        try{
            String jsonString = getJsonString(email, messageType);
            callWebexAPI(jsonString);
        }catch (JsonProcessingException | WebClientResponseException e){
            Main.logger.warn(e.getMessage());
        }
    }

    // Send message with custom text
    public void sendMessage(String email, String messageText){
        try{
            String jsonString = getJsonString(email, messageText);
            callWebexAPI(jsonString);

        }catch (JsonProcessingException | WebClientResponseException e){
            Main.logger.warn(e.getMessage());
        }
    }

    // Build the Body of the request aka the message we will pass to the Webex API
    // Handles pre-defined messages, defined at the top of this service
    private String getJsonString(String email, WebexMessageType messageType) throws JsonProcessingException {
        Map<String, String> json = new HashMap<>();
        json.put("toPersonEmail", email);

        switch (messageType) {
            case PASSWORD_RESET ->
                json.put("text", PASSWORD_RESET_MESSAGE);
            case LOGIN ->
                json.put("text", LOGIN_MESSAGE);
            case LEAVE_APPROVED ->
                json.put("text", LEAVE_APPROVED_MESSAGE);
            default -> throw new IllegalStateException("Unexpected value: " + messageType);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(json);
    }

    // Build the Body of the request aka the message we will pass to the Webex API
    // Handles custom messages, passed into this method by the caller
    private String getJsonString(String email, String messageText) throws JsonProcessingException {
        Map<String, String> json = new HashMap<>();
        json.put("toPersonEmail", email);
        json.put("text", messageText);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(json);
    }
}
