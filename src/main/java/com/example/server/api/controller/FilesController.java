package com.example.server.api.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.api.services.fileService.FileService;


@RestController
@CrossOrigin("*")
@RequestMapping("/files")
public class FilesController {
  private final String WRITE_FILE = "/write-file";

  @PostMapping(WRITE_FILE)
  public String writeFile(
    @RequestParam("file") String file,
    @RequestParam("email") String email,
    @RequestParam("fileName") String fileName,
    @RequestParam("lastModified") String lastModified,
    @RequestParam("docId") String docId
  ){
    JSONObject response = new FileService()
    .createFileDoc(file, docId, email, fileName, lastModified);
    return response.toString();
  }
}