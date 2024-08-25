package com.oracle.service;

import java.sql.Statement;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import com.oracle.model.DBConfig;

@Service
public class OracleService {

     public String extractOracle(DBConfig dbConfig) {
          // String jdbcUrl = "jdbc:oracle:thin:@//hostname:1521/" + dbConfig.getDbName();
          // String username = dbConfig.getUsername();
          // String password = dbConfig.getPwd();
          // String query = dbConfig.getQuery();
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
          String timestamp = LocalDateTime.now().format(formatter);
          String filePath = "D:\\Output\\ORACLE_SERVICE\\" + dbConfig.getFileName() + "_" + timestamp + ".csv";
          try {
               System.out.println(dbConfig.getUsername());
               System.out.println(dbConfig.getPwd());
               Class.forName("oracle.jdbc.driver.OracleDriver");
               try (
                         Connection conn = DriverManager.getConnection(
                                   "jdbc:oracle:thin:@localhost:1521:" + dbConfig.getDbName(), dbConfig.getUsername(),
                                   dbConfig.getPwd());
                         Statement stmt = conn.createStatement();
                         ResultSet resultSet = stmt.executeQuery(dbConfig.getQuery());
                         FileWriter writer = new FileWriter(filePath)) {

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

          return "Oracle CSV file created Successfully: " + filePath;
     }
}
