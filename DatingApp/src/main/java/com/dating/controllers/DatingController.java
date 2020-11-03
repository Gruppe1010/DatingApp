package com.dating.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DatingController
{

    @GetMapping("/")
    public String index()
    {
        return "startpage";
    }


}
