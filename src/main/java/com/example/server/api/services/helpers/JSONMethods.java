package com.example.server.api.services.helpers;

import org.json.JSONObject;

public class JSONMethods {
  private JSONObject jsonObject = new JSONObject();

  public JSONObject createdUser(String docId, String email){
    return new JSONObject()
    .put("docId", docId)
    .put("email", email);
  }

  public JSONObject createResponse(String message, int code){
    return jsonObject
    .put("message", message)
    .put("code", code);
  }

  public JSONObject createResponse(JSONObject message, int code){
    return new JSONObject()
    .put("message", message)
    .put("code", code);
  }

  public JSONObject getResponse(){return jsonObject;}
}