package com.sda.productionproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class HomePageController {

    @RequestMapping({"", "/", "/index"})
    public String getHomePage() {
        return "index";
    }
}
