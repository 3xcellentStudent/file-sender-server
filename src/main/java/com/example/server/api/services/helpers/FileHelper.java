package com.example.server.api.services.helpers;

import java.util.HashMap;

public class FileHelper {
  public HashMap<String, Object> createData(
    String fileBytes, String fileName, String lastModified
  ){
    HashMap<String, Object> hashMap = new HashMap<>();
    hashMap.put("file", fileBytes);
    hashMap.put("fileName", fileName);
    hashMap.put("lastModified", lastModified);
    hashMap.put("createdAt", System.currentTimeMillis());
    return hashMap;
  }
}