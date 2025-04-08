package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/port")
public class InfoController {

    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    @GetMapping
    public Integer getPort() {
        int port = webServerAppCtxt.getWebServer().getPort();
        return port;
    }
}
//mvn clean install