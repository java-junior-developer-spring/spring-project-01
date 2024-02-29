package com.itmo.springproject01.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itmo.springproject01.entity.Genre;
import com.itmo.springproject01.entity.Picture;
import com.itmo.springproject01.exception.ShopException;
import com.itmo.springproject01.helper.PictureParamEditor;
import com.itmo.springproject01.service.GenreService;
import com.itmo.springproject01.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@Controller // @Controller + @ResponseBody
@RequestMapping("/picture")
public class PictureController {
    private final PictureService pictureService;
    private final GenreService genreService;
    private final ObjectMapper objectMapper;

    @Autowired
    public PictureController(PictureService pictureService, GenreService genreService, ObjectMapper objectMapper) {
        this.pictureService = pictureService;
        this.genreService = genreService;
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

    @Secured("ROLE_ADMIN")
    @ResponseBody
    @PostMapping
    public ResponseEntity<Void> addPicture(@RequestPart Picture picture,
                                           @RequestPart String genreUrl,
                                           @RequestPart MultipartFile image) {
        try {
            int pictureId = pictureService.saveNewPicture(picture, genreUrl, image);
            URI location = URI.create("http://127.0.0.1:8080/picture/" + pictureId);
            return ResponseEntity.created(location).build();
        } catch (ShopException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // import org.springframework.ui.Model;
    @GetMapping("/{pictureId}")
    public String getPictureById(@PathVariable int pictureId, Model model) {
        try {
            Picture picture = pictureService.getPictureById(pictureId);
            model.addAttribute("pictureInfo", picture);
            Iterable<Genre> genreList = genreService.getAllGenres();
            model.addAttribute("genres", genreList);
            // имя html файла, который находится в папке templates
            return "picture";
        } catch (ShopException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/genre/{genreUrl}")
    public String getPictureById(@PathVariable String genreUrl, Model model) {
        List<Picture> picturesList = pictureService.getPicturesByGenre(genreUrl);
        Iterable<Genre> genreList = genreService.getAllGenres();
        model.addAttribute("pictures", picturesList);
        model.addAttribute("genres", genreList);
        // имя html файла, который находится в папке templates
        return "pictures";
    }

    @GetMapping("/not_found")
    public String notFound() {
        return "not_found";
    }


    @GetMapping // ?page=0&size=2
    public String getPictures(@RequestParam int page, @RequestParam int size, Model model) {
        List<Picture> picturesList = pictureService.getPictures(page, size);
        /*if (picturesList == null || picturesList.isEmpty()){
            return "pictures_not_found"; // "redirect:picture/not_found";
        }*/
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

    /*
    @GetMapping("/void01")
    public String void01(*//*@RequestParam String key, @RequestParam String key1*//*){
        return "void01"; // void01.html
    }

    // <p th:text="${param.key}"></p> value
    // <p th:text="${param.key1}"></p> value1

    @GetMapping("/void02")
    public String void02(){
        return "redirect:picture/void01?key=value&key1=value1";
        return "redirect:picture/void01?failed";
    }
    */



}
