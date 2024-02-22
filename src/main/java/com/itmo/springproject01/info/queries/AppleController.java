package com.itmo.springproject01.info.queries;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/apple")
public class AppleController {

    @PostMapping // @Controller
    public String addApple01(Apple apple,
                             @ModelAttribute MultipartFile file){
        return "";
    }

    @PostMapping // @RestController
    @ResponseBody
    public String addApple02(@RequestPart Apple apple,
                             @RequestPart MultipartFile file){
        return "";
    }
}
