package com.itmo.springproject01.controller;

import com.itmo.springproject01.exception.ShopException;
import com.itmo.springproject01.service.AdminAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/admin")
public class AdminAccountController {
    private AdminAccountService adminAccountService;

    @Autowired
    public AdminAccountController(AdminAccountService adminAccountService) {
        this.adminAccountService = adminAccountService;
    }

    @PostMapping("/authorize")
    public String authorize(@RequestParam String login,
                            @RequestParam String password) {
        try {
            return adminAccountService.authorize(login, password);
        } catch (ShopException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
