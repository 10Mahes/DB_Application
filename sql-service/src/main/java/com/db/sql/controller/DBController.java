package com.db.sql.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.db.sql.model.DBConfig;
import com.db.sql.service.DBService;

@RestController
public class DBController {

     @Autowired
     private DBService dbService;

     @PostMapping("/details")
     public String exportCsv(@RequestBody DBConfig request) {
          try {
               return dbService.exportToCsv(request);
          } catch (IOException e) {
               return "Error occurred: " + e.getMessage();
          }
     }
}
