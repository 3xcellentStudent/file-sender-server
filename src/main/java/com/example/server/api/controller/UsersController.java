package com.example.server.api.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.api.services.userService.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UsersController {
  private final String CHECK_NAME = "/check-name";
  private UserService userService = new UserService();
  
  @PostMapping(CHECK_NAME)
  public String checkFileName(@RequestBody String body){
    JSONObject jsonBody = new JSONObject(body);
    String email = jsonBody.getString("email");
    String fileName = jsonBody.getString("fileName");
    String address = jsonBody.getString("address");
    // System.out.println("---------------------------------------------------------");
    // System.out.println(new EncryptionUtil().decrypt(address));
    // System.out.println("---------------------------------------------------------");

    String response = userService.checkUser(email, fileName, address).toString();
    return response;
  }
}