package com.itmo.springproject01.service;

import com.itmo.springproject01.entity.Genre;
import com.itmo.springproject01.entity.Picture;
import com.itmo.springproject01.exception.ShopException;
import com.itmo.springproject01.repository.GenreRepository;
import com.itmo.springproject01.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class PictureService {
    private final PictureRepository pictureRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public PictureService(PictureRepository pictureRepository, GenreRepository genreRepository) {
        this.pictureRepository = pictureRepository;
        this.genreRepository = genreRepository;
    }

    public Picture getPictureById(int pictureId) throws ShopException {
        return pictureRepository.findById(pictureId)
                .orElseThrow(()->new ShopException("Картина с id = " +
                        pictureId + "не найдена"));
    }

    public List<Picture> getPicturesByGenre(String genreUrl){
        return pictureRepository.findAllByGenreUrl(genreUrl);
    }

    public List<Picture> getPictures(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return pictureRepository.findAll(pageable).getContent();
    }

    public Page<Picture> getPicturePage(int page, int size){
        // sort
        // Sort sort = Sort.by("name").descending()
                // .and(Sort.by("createdAt").ascending());
        // limit + offset
        // Pageable pageable = PageRequest.of(page, size);
        // limit + offset + sort
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return pictureRepository.findAll(pageable);
    }

    // name, description, createdAt, ?? imagePath ??
    // genreUrl
    // изображение
    public int saveNewPicture(Picture picture, String genreUrl, MultipartFile image)
            throws ShopException {
        Genre genre = genreRepository.findById(genreUrl)
                .orElseThrow(()->new ShopException("Жанра не существует"));
        picture.setImagePath(saveFile(image));
        picture.setGenre(genre);
        return pictureRepository.save(picture).getId();
    }

    private String saveFile(MultipartFile file) throws ShopException {
        // TODO:: проверка на тип и размер файла
        String fileName = "/path_to_file_storage/" + UUID.randomUUID();
        try {
            Files.write(Path.of(fileName), file.getBytes());
            return fileName;
        } catch (IOException e) {
            throw new ShopException("Проблема загрузки файла " + e.getMessage());
        }
    }
}
