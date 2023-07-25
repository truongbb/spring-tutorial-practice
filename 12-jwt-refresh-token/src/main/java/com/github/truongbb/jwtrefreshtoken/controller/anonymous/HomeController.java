package com.github.truongbb.jwtrefreshtoken.controller.anonymous;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/jd-page")
    public String jdPage() {
        return "jd-page";
    }

}
