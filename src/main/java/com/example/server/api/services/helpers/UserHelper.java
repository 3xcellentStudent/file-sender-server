package com.example.server.api.services.helpers;

import java.util.HashMap;

public class UserHelper {

  public HashMap<String, Object> createData(String email, String address){
    HashMap<String, Object> emailHashMap = new HashMap<>();
    emailHashMap.put("email", email);
    emailHashMap.put("address", address);
    return emailHashMap;
  }

  public HashMap<String, Object> userPushNewFile(String docId, String fileName){
    HashMap<String, Object> newFileHashMap = new HashMap<>();
    newFileHashMap.put("docId", docId);
    newFileHashMap.put("fileName", fileName);
    return newFileHashMap;
  } 
}