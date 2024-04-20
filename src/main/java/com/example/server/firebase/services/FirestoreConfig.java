package com.example.server.firebase.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

@Configuration
public class FirestoreConfig {

  @Bean
  public Firestore initialize(){
    // String jsonKey = FirestoreConfig.class.getResource(resourcesJsonFile).getPath();
    String resourcesJsonFile = "/static/file-sender-project-d0737566caaa.json";
    // URL jsonKey = FirestoreConfig.class.getResource(resourcesJsonFile);
    // System.out.println(jsonKey.toString());
    // String pathToProject = new File("").getAbsolutePath();
    // String relativePathFile = "/src/main/resources/static/file-sender-project-d0737566caaa.json";
    // String jsonKey = FirestoreConfig.class.getResource(resourcesJsonFile).toString().substring(5);
    try {
      // FileInputStream refreshToken = new FileInputStream(jsonKey.toString());
      InputStream refreshToken = FirestoreConfig.class.getResourceAsStream(resourcesJsonFile);
      FirestoreOptions options = FirestoreOptions.newBuilder()
      .setCredentials(GoogleCredentials.fromStream(refreshToken))
      .setProjectId("file-sender-project")
      .setDatabaseId("file-sender-database")
      .build();
      return options.getService();
    } catch (IOException error){
      System.out.println(error.getMessage());
      error.printStackTrace();
      return null;
    }
  }
}