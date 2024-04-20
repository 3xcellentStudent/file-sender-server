package com.example.server.api.services.fileService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.server.api.services.helpers.FileHelper;
import com.example.server.api.services.helpers.JSONMethods;
import com.example.server.api.services.userService.UserService;
import com.example.server.firebase.services.FirestoreConfig;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;


public class FileService {
  private UUID uuid = UUID.randomUUID();
  private JSONMethods jsonMethods = new JSONMethods();
  private String FILES_COLLECTION = "files";
  private FileHelper fileHelper = new FileHelper();
  private UserService userService = new UserService();
  private JSONObject localResponse = new JSONObject();

  @Autowired
  private Firestore firestore = new FirestoreConfig().initialize();
  private CollectionReference filesCollection = firestore.collection(FILES_COLLECTION);
  
  public JSONObject createFileDoc(
    String fileBytes, String docId, String email, String fileName, String lastModified
  ){
    String fileId = uuid.toString().substring(0, 20);
    HashMap<String, Object> dataHashMap = fileHelper
    .createData(fileBytes, fileName, lastModified);
    ApiFuture<WriteResult> futureResult = filesCollection.document(fileId).set(dataHashMap);
    try {
      if(futureResult.isCancelled()){
        return jsonMethods.createResponse("File was not created", 400);
      } else return userService.pushNewFile(docId, fileId, fileName);
    } catch (Exception error) {
      System.err.println(error.getMessage());
      error.printStackTrace();
      return jsonMethods.createResponse(
        "Something went wrong while creating the file !", 500
      );
    }
  }

  public JSONObject findFile(String email, String docId, Object fileObject, String fileName){
    if(fileObject == null){
      return jsonMethods.createResponse(docId, 404);
    } else {
      List<Map<String, String>> fileList = (List<Map<String, String>>) fileObject;
      for(Map<String, String> fileMap : fileList){
        JSONObject jsonObject = new JSONObject(fileMap);
        String fileNameDb = jsonObject.getString("fileName");
        String fileIdDb = jsonObject.getString("fileName");
        if(fileNameDb.equals(fileName)){
          localResponse = jsonMethods
          .createResponse("File with ID: " + fileIdDb + " already exists", 409);
        }
        else {
          JSONObject message = new JSONObject().put("docId", docId).put("email", email);
          localResponse = jsonMethods.createResponse(message, 200);
        }
      }
    }
    return localResponse;
  }
}