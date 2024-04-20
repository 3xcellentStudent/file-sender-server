package com.example.server.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
public class MainController {
  
  @GetMapping("/")
  private String getEndpoint(){
    return "Hello, it's main page !";
  }
}