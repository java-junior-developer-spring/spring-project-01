package com.itmo.springproject01.service;

import com.itmo.springproject01.entity.Picture;
import com.itmo.springproject01.exception.ShopException;
import com.itmo.springproject01.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureService {
    private final PictureRepository pictureRepository;

    @Autowired
    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
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
}
