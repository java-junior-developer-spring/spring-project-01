package com.itmo.springproject01.info.front;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

public class TaskAjaxRequestDto {
    private MultipartFile doc;
    private String task;

    public MultipartFile getDoc() {
        return doc;
    }

    public void setDoc(MultipartFile doc) {
        this.doc = doc;
    }

    public Task getTask() {
        try {
            return new ObjectMapper().readValue(task, Task.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTask(String task) {
        this.task = task;
    }
}
