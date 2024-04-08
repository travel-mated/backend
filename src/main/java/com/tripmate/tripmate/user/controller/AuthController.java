package com.tripmate.tripmate.user.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/")
    public String mainAPI() {
        return "main route";
    }

    @GetMapping("/my")
    public String myAPI() {
        return "my route";
    }
}
