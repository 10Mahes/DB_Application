package com.oracle.controller;

import org.springframework.web.bind.annotation.RestController;

import com.oracle.model.DBConfig;
import com.oracle.service.OracleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class OracleController {

     @Autowired
     private OracleService oracleService;

     @PostMapping("/query")
     public ResponseEntity<String> executeQuery(@RequestBody DBConfig request) {
          try {
               System.out.println("Inside Controllerd");
               String results = oracleService.extractOracle(request);
               return ResponseEntity.ok(results);
          } catch (Exception e) {
               return ResponseEntity.status(500).body("Error");
          }
     }

}
