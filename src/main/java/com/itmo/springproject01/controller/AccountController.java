package com.itmo.springproject01.controller;

import com.itmo.springproject01.entity.CustomUser;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/account")
public class AccountController {
    /*
    @GetMapping("/registration")
    public String getRegistrationForm(){
        return "registrationForm";
    }
    */

    @GetMapping("/registration")
    public String getRegistrationForm(CustomUser user){
        return "registrationForm";
    }

    /*
    <input type="text" name="login">
    <input type="password" name="userpassword">
    @PostMapping("/registration")
    public String addNewUser(@RequestParam String login,
                             @RequestParam String userpassword){

    }
    */
    /* form th:object="${user}"
    <input type="text" th:field="*{username}">
    <input type="password" th:field="*{password}">*/
    @PostMapping("/registration")
    public String addNewUser(@Valid CustomUser user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "registrationForm";
        System.out.println(user.getUsername());
        return "redirect:account/login";
    }

    @GetMapping("/login")
    public String login(){
        // для работы spring security передавать данные в форму не нужно
        // для обеспечения остального функционала в html можно передать любые данные
        return "login";
    }

    @GetMapping
    public String account(
            /* Authentication authentication - для получения подробной информации нужен cast */
            /* Principal principal - содержит только username (getName()) */
            /* @AuthenticationPrincipal UserDetails userDetails */
            /* HttpServletRequest request */
    ){
        // Можно получить в контроллерах и сервисах
        /*Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();*/
        /*UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        org.springframework.security.core.userdetails.CustomUser user =
                (org.springframework.security.core.userdetails.CustomUser)
                        authentication.getPrincipal();*/
        // CustomUser customUser = (CustomUser) authentication.getPrincipal();
        // CustomUser customUser = (CustomUser) userDetails;
        // Principal principal = request.getUserPrincipal();

        return "account";
    }





}
