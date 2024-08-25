package com.api.test.conponent;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ApiScheduler {

     static int start1 = 0;
     static int end1 = 10000;

     static int start2 = 0;
     static int end2 = 10000;

     private final RestTemplate restTemplate;

     public ApiScheduler() {
          this.restTemplate = new RestTemplate();
     }

     @Async
     @Scheduled(cron = "0 */5 * * * ?")
     public void callSQL() {
          try {
               System.out.println("Inside callAPI function");
               String url = "http://localhost:3031/details";

               Map<String, Object> jsonMap = new HashMap<>();
               jsonMap.put("username", "sa");
               jsonMap.put("password", "root");
               jsonMap.put("query", "select * from Customer where CustomerID between " +
                         start1 + " AND " + end1);
               jsonMap.put("filename", "SQL_Customer");
               jsonMap.put("databaseName", "customer");

               start1 = end1 + 1;
               end1 = end1 + 10000;

               // Convert Map to JSON string
               ObjectMapper objectMapper = new ObjectMapper();
               String jsonData = objectMapper.writeValueAsString(jsonMap);
               System.out.println(jsonData);

               // Set headers and content type
               HttpHeaders headers = new HttpHeaders();
               headers.setContentType(MediaType.APPLICATION_JSON);

               HttpEntity<String> request = new HttpEntity<>(jsonData, headers);
               System.out.println("calling the api");

               String response = restTemplate.exchange(url, HttpMethod.POST, request,
                         String.class).getBody();

               System.out.println("API Response" + response);
          } catch (Exception e) {
               System.err.println("Error while calling API: " + e.getMessage());
          }

          System.out.println("Done calling");
     }

     @Async
     @Scheduled(cron = "0 */5 * * * ?")
     public void callOracle() {
          try {
               System.out.println("Inside callAPI function");
               String url = "http://localhost:3032/query";

               Map<String, Object> jsonMap = new HashMap<>();
               jsonMap.put("username", "system");
               jsonMap.put("pwd", "root");
               jsonMap.put("query", "select * from Customer where CustomerID between " + start2 + " AND " + end2);
               jsonMap.put("fileName", "Oracle_Customer");
               jsonMap.put("dbName", "XE");

               start2 = end2 + 1;
               end2 = end2 + 10000;

               // Convert Map to JSON string
               ObjectMapper objectMapper = new ObjectMapper();
               String jsonData = objectMapper.writeValueAsString(jsonMap);
               System.out.println(jsonData);

               // Set headers and content type
               HttpHeaders headers = new HttpHeaders();
               headers.setContentType(MediaType.APPLICATION_JSON);

               HttpEntity<String> request = new HttpEntity<>(jsonData, headers);
               System.out.println("calling the api");

               String response = restTemplate.exchange(url, HttpMethod.POST, request, String.class).getBody();

               System.out.println("API Response: " + response);
          } catch (Exception e) {
               System.err.println("Error while calling API: " + e.getMessage());
          }

          System.out.println("Done calling");
     }
}
