package com.db.sql.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DBConfig {

     private String username;
     private String password;
     private String query;
     private String filename;
     private String databaseName;
}
