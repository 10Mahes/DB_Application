package com.db.sql.service;

import java.sql.Statement;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.db.sql.model.DBConfig;

@Service
public class DBService {

     // @Value("${spring.datasource.driver-class-name}")
     // private String driverClassName;
     private String deriverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

     public String exportToCsv(DBConfig request) throws IOException {
          String jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=" + request.getDatabaseName()
                    + ";encrypt=true;trustServerCertificate=true;Trusted_Connection=True";
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
          String timestamp = LocalDateTime.now().format(formatter);
          String fileName = "D:\\Output\\" + request.getFilename() + "_" + timestamp + ".csv";

          try {
               // Load the SQL Server JDBC driver
               Class.forName(deriverClass);
               // Creating Connection with sql service
               try (Connection conn = DriverManager.getConnection(jdbcUrl, request.getUsername(),
                         request.getPassword());
                         Statement stmt = conn.createStatement();
                         ResultSet resultSet = stmt.executeQuery(request.getQuery());
                         FileWriter writer = new FileWriter(fileName)) {

                    int columnCount = resultSet.getMetaData().getColumnCount();

                    // Write header
                    for (int i = 1; i <= columnCount; i++) {
                         writer.write(resultSet.getMetaData().getColumnName(i));
                         if (i < columnCount)
                              writer.write(",");
                    }
                    writer.write("\n");

                    // Write data rows
                    while (resultSet.next()) {
                         for (int i = 1; i <= columnCount; i++) {
                              writer.write(resultSet.getString(i));
                              if (i < columnCount)
                                   writer.write(",");
                         }
                         writer.write("\n");
                    }
               }
          } catch (Exception e) {
               return "Error occurred: " + e.getMessage();
          }

          return "CSV file created: " + fileName;
     }
}
