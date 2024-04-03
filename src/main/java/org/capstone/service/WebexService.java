package org.capstone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.capstone.Main;
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
    @Autowired
    public WebexService(WebClient webClient){
        this.webClient = webClient;
    }

    public void sendPasswordHasBeenResetMessage(String email) throws JsonProcessingException {
        Map<String, String> json = new HashMap<>();
        json.put("toPersonEmail", email);
        json.put("text", "ALERT! Your password has been reset. If this was not you, please contact security. Unfortunately for you, our security department does not exist - this being a nonexistent company and all. Good luck!");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(json);

        try{
            webClient.post()
                    .uri("https://webexapis.com/v1/messages")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + BOT_ACCESS_TOKEN)
                    .bodyValue(jsonString)
                    .retrieve()
                    .toBodilessEntity()
                    .subscribe(
                            responseEntity -> {
                                // Handle success response here
                                Main.logger.info("Status: " + responseEntity.getStatusCode().value());
                                Main.logger.info("Response Body : " + responseEntity.getBody());
                            },
                            error -> {
                                // Handle the error here
                                if (error instanceof WebClientResponseException) {
                                    WebClientResponseException ex = (WebClientResponseException) error;
                                    HttpStatusCode status = ex.getStatusCode();
                                    Main.logger.info("Error Status Code: " + status.value());

                                    //...
                                } else {
                                    // Handle other types of errors
                                    Main.logger.warn("An unexpected error occurred: " + error.getMessage());
                                }
                            }
                    );
        } catch(WebClientResponseException e){
            Main.logger.warn(e.getResponseBodyAsString());
        }
    }
}
