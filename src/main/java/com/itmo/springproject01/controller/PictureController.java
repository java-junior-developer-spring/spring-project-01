package com.itmo.springproject01.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itmo.springproject01.entity.Genre;
import com.itmo.springproject01.entity.Picture;
import com.itmo.springproject01.helper.PictureParamEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/picture")
public class PictureController {
    private final ObjectMapper objectMapper;

    @Autowired
    public PictureController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // @RequestParam + Jackson
    /*
    @PostMapping // picture: {"name": "Название", "description": ""}
    public ResponseEntity<Void> addPicture(@RequestParam String pictureString){
        ObjectMapper objectMapper = new ObjectMapper();
        Picture picture = objectMapper.readValue(pictureString, Picture.class);
    }
    */
    @PostMapping // picture: {"name": "Название", "description": ""}
    public ResponseEntity<Void> addPicture(@RequestParam Picture picture){

    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.registerCustomEditor(Picture.class, new PictureParamEditor(objectMapper));
    }

}
