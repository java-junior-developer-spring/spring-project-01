package com.itmo.springproject01.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itmo.springproject01.entity.Picture;

import java.beans.PropertyEditorSupport;

public class PictureParamEditor extends PropertyEditorSupport {

    private final ObjectMapper objectMapper;

    public PictureParamEditor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text.isEmpty()) {
            setValue(null);
        } else {
            Picture picture;
            try {
                picture = objectMapper.readValue(text, Picture.class);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException(e);
            }
            setValue(picture);
        }
    }

}