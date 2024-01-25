package com.itmo.springproject01.service;

import com.itmo.springproject01.entity.Genre;
import com.itmo.springproject01.exception.ShopException;
import com.itmo.springproject01.repository.GenreRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public String saveGenre(Genre genre) throws ShopException {
        if (genreRepository.existsById(genre.getUrl())){
            throw new ShopException("Жанр с таким url уже существует");
        }
        return genreRepository.save(genre).getUrl();
    }
}
