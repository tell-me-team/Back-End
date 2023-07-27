package com.tellme.tellme.healcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class healthcheckController {
    @GetMapping("/healthcheck")
    public String healthcheck(){
        return "ok";
    }

}

