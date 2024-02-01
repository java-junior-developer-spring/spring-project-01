package com.itmo.springproject01.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itmo.springproject01.entity.Picture;
import com.itmo.springproject01.exception.ShopException;
import com.itmo.springproject01.helper.PictureParamEditor;
import com.itmo.springproject01.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller // @Controller + @ResponseBody
@RequestMapping("/picture")
public class PictureController {
    private final PictureService pictureService;
    private final ObjectMapper objectMapper;

    @Autowired
    public PictureController(PictureService pictureService, ObjectMapper objectMapper) {
        this.pictureService = pictureService;
        this.objectMapper = objectMapper;
    }

    // picture: {"pictureName": "Название", "description": "Описание", "createdAt":"01-01-2024"}
    // @RequestParam + Jackson
    /*
    @PostMapping // picture: {"name": "Название", "description": ""}
    public ResponseEntity<Void> addPicture(@RequestParam String pictureString){
        ObjectMapper objectMapper = new ObjectMapper();
        Picture picture = objectMapper.readValue(pictureString, Picture.class);
    }
    */

    /*
    @GetMapping("/v1")
    public String v1(){
        return "v1";
    }

    @ResponseBody
    @GetMapping("/v2")
    public String v2(){
        return "v2";
    }
    */


    /*@ResponseBody
    @PostMapping
    public ResponseEntity<Void> addPicture(@RequestPart Picture picture,
                                           @RequestPart MultipartFile image){
        // /uploads/ngjegh3598jfq.png
        // picture.setImagePath("/uploads/ngjegh3598jfq.png");
    }*/

    // import org.springframework.ui.Model;
    @GetMapping("/{pictureId}")
    public String getPictureById(@PathVariable int pictureId, Model model) {
        try {
            Picture picture = pictureService.getPictureById(pictureId);
            model.addAttribute("pictureInfo", picture);
            // имя html файла, который находится в папке templates
            return "picture";
        } catch (ShopException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/genre/{genreUrl}")
    public String getPictureById(@PathVariable String genreUrl, Model model) {
        List<Picture> picturesList = pictureService.getPicturesByGenre(genreUrl);
        model.addAttribute("pictures", picturesList);
        // имя html файла, который находится в папке templates
        return "pictures";
    }

    @GetMapping
    public String getPictures(@RequestParam int page, @RequestParam int size, Model model) {
        List<Picture> picturesList = pictureService.getPictures(page, size);
        model.addAttribute("pictures", picturesList);
        // имя html файла, который находится в папке templates
        return "pictures";
    }

    @ResponseBody
    @GetMapping("/json") //?номер_страницы=1&количество_элементов=10
    public Page<Picture> getPicturePage(@RequestParam int page, @RequestParam int size) {
        return pictureService.getPicturePage(page, size);
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Picture.class, new PictureParamEditor(objectMapper));
    }

}
