package com.itmo.springproject01.controller;

import com.itmo.springproject01.entity.Genre;
import com.itmo.springproject01.exception.ShopException;
import com.itmo.springproject01.service.GenreService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/genre")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    // в теле сообщения
    // @RequestBody Genre genre {}
    // @RequestParam String name
    // @RequestParam int age

    // в строке запроса
    // /genre?id=3&name=zzz
    // @RequestParam int id
    // @RequestParam String name
    // /genre/id/3/name/zzz
    // /genre/3
    // @GetMapping("/id/{idValue}/name/{nameValue}")
    // @PathVariable int idValue
    // @PathVariable String nameValue
    //@PostMapping
    //@RequestMapping(path = "/add", // /genre
    //        method = RequestMethod.POST)
    /*public Genre addGenre(@RequestBody Genre genre){

    }*/
    @PostMapping// 201 CREATED header Location:
    public ResponseEntity<Void> addGenre(@RequestBody @Valid Genre genre){
        try {
            String genreUrl = genreService.saveGenre(genre);
            URI location = URI.create("http://127.0.0.1:8080/genre/" + genreUrl);
            return ResponseEntity.created(location).build();
        } catch (ShopException e) {
            // import org.springframework.http.HttpStatus;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public Genre update(@RequestBody Genre genre){

    }
    // /genre/dwef/archive/true
    // /genre/dwef/archive/false
    @PatchMapping("/{url}/archive/{value}") // 204
    public ResponseEntity<Void> archive(@Size(min = 3, max = 10) @PathVariable String url,
                                        @PathVariable boolean value){

    }
    @GetMapping
    public List<Genre> genres(){

    }






}
