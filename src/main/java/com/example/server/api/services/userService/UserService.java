package com.example.server.api.services.userService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.server.api.services.fileService.FileService;
import com.example.server.api.services.helpers.JSONMethods;
import com.example.server.api.services.helpers.UserHelper;
import com.example.server.firebase.services.FirestoreConfig;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;

public class UserService {
  private String USER_COLLECTION = "users";
  private JSONMethods jsonMethods = new JSONMethods();
  private JSONObject localResponse;
  private UserHelper userHelper = new UserHelper();

  @Autowired
  private Firestore firestore = new FirestoreConfig().initialize();
  private CollectionReference userCollection = firestore.collection(USER_COLLECTION);

  private JSONObject iterateDocuments(
    String email, List<QueryDocumentSnapshot> userDocuments, String fileName, String address
  ){
    userDocuments.forEach(doc -> {
      localResponse = new FileService()
      .findFile(email, doc.getId(), doc.get("files"), fileName);
      if(localResponse.get("code").equals(404)){
        List<Map<String, Object>> filesList = new ArrayList<>();

        ApiFuture<WriteResult> filesIsUpdated = userCollection
        .document(doc.getId()).update("files", filesList);
        ApiFuture<WriteResult> addressIsUpdated = userCollection
        .document(doc.getId()).update("address", address);

        if(filesIsUpdated.isCancelled() || addressIsUpdated.isCancelled()){
          localResponse = jsonMethods
          .createResponse("Files or address updating failed !", 500);
          return;
        } else return;
      }
      else{
        return;
      }
    });
    return localResponse;
  }
  
  public JSONObject checkUser(String email, String fileName, String address){
    try {
      Query userEmailQuery = userCollection.whereEqualTo("email", email);
      List<QueryDocumentSnapshot> userDocuments = userEmailQuery.get().get().getDocuments();

      if(userDocuments.isEmpty()){return createUser(email, address);}
      else {return iterateDocuments(email, userDocuments, fileName, address);}

    } catch (Exception error) {
      System.err.println(error.getMessage());
      error.printStackTrace();
      return jsonMethods.createResponse("Server error...", 500);
    }
  }

  private JSONObject createUser(String email, String address){
    HashMap<String, Object> dataObject = userHelper.createData(email, address);
    try {
      DocumentSnapshot snapshot = userCollection.add(dataObject).get().get().get();
      String docId = snapshot.getId();
      String response = snapshot.getData().get("email").toString();
      JSONObject user = jsonMethods.createdUser(docId, response);

      return jsonMethods.createResponse(user, 200);
    } catch(Exception error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return jsonMethods.createResponse("Failed to create E-mail", 500);
    }
  }

  public JSONObject pushNewFile(String docId, String fileId, String fileName){
    HashMap<String, Object> newFile = userHelper.userPushNewFile(fileId, fileName);
    try {
      ApiFuture<WriteResult> result = userCollection.document(docId)
      .update("files", FieldValue.arrayUnion(newFile));

      if(result.isCancelled()){
        return jsonMethods
        .createResponse("Database error: couldn't create new object !", 500);
      }
      else {
        return jsonMethods
        .createResponse("File was successfully recorder with hash: " + fileId, 201);
      }
    } catch (Exception error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return jsonMethods
      .createResponse("File was not recorder !", 500);
    }
  }
}