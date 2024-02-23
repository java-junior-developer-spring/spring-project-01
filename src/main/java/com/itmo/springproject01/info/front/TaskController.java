package com.itmo.springproject01.info.front;

import com.itmo.springproject01.info.front.Task;
import com.itmo.springproject01.info.front.TaskAjaxRequestDto;
import com.itmo.springproject01.info.front.TaskAjaxResponseDto;
import com.itmo.springproject01.info.front.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/front")
public class TaskController {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // форма, данные которой будут отправляться с перезагрузкой страницы,
    // на js осуществляется предварительная проверка данных и возможность добавить участников
    @GetMapping("/addtask/reload")
    public String getAddTaskFormReload(Task task) {
        return "info/task/addtask-form-reload";
    }

    // данные приходят обычным запросом
    // сервер должен либо сгенерировать html, либо выполнить перенаправление
    @PostMapping("/addtask/reload")
    public String addNewTaskReload(Task task,
                                   @RequestParam MultipartFile doc,
                                   @RequestParam(required = false, value = "participants[]") String[] participants) {
        // 1. Task task - собирается благодяря th:object
        // 2. @RequestParam MultipartFile doc - значение атрибута name инпута выбора файла
        // соответствует имени переменной (в данном случе doc)
        // 3. @RequestParam("participants[]" String[] participants - если из формы
        // необходимо передать массив данных. Обязательно ("participants[]"),
        // где participants[] - значение атрибута name инпутов, данные которых должны приходить массивом

        System.out.println(task.getTitle());
        System.out.println(doc.getOriginalFilename());
        System.out.println(Arrays.toString(participants));

        task.setDocumentName(doc.getOriginalFilename());

        if (participants != null) task.setParticipants(
                Arrays.stream(participants)
                        .filter(p -> p != null && !p.isEmpty())
                        .collect(Collectors.joining())
        );

        try {
            Files.write(Path.of(doc.getOriginalFilename()), doc.getBytes());
        } catch (Exception e) {
            return "redirect:/front/addtask/reload?load_error";
        }

        return "redirect:task/" + taskRepository.save(task).getId();
    }

    // форма, данные которой будут отправляться
    // аякс запросом из js без перезагрузки страницы
    @GetMapping("/addtask/ajax")
    public String getAddTaskFormAjax() {
        return "info/task/addtask-form-ajax";
    }

    // данные приходят от js аякс запросом
    // ответ будет обрабатываться js кодом, html страницу генерировать не нужно
    @PostMapping(value = "/addtask/ajax", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public TaskAjaxResponseDto addNewTaskAjax(@ModelAttribute TaskAjaxRequestDto taskAjaxRequestDto) {
        System.out.println(taskAjaxRequestDto.getTask().getTitle());
        System.out.println(taskAjaxRequestDto.getDoc().getOriginalFilename());

        Task task = taskAjaxRequestDto.getTask();
        MultipartFile doc = taskAjaxRequestDto.getDoc();

        task.setDocumentName(doc.getOriginalFilename());

        try {
            Files.write(Path.of(doc.getOriginalFilename()), doc.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new TaskAjaxResponseDto(taskRepository.save(task).getId(), task.getDocumentName());
    }

    // вернуть html страницу
    @GetMapping("/tasks/ajax")
    public String getTasksHtml(){
        return "info/task/tasks-ajax";
    }

    // вернуть html страницу
    @GetMapping("/tasks/ajax/all")
    @ResponseBody
    public Iterable<Task> getTasksAjax(){
        return taskRepository.findAll();
    }

}
