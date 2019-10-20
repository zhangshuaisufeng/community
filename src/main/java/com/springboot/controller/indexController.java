package com.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Console;

@Controller
public class indexController {
    @GetMapping("/index")
    public String indexController(){
        System.out.print(111);
        return "index";
    }
}
