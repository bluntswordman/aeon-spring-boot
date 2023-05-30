package com.finalaeonproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HalloController {
    @GetMapping
    public String hallo() {
        return "<h1 style='color:blue;text-align:center;'>Hai, if you see this message, it means that application is running well</h1>";
    }
}
