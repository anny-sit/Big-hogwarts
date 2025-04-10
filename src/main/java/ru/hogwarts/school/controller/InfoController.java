package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoServiceImpl;

import java.util.stream.Stream;

@RestController
@RequestMapping("/port")
public class InfoController {

    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    private final InfoServiceImpl infoService;

    public InfoController(InfoServiceImpl infoService) {
        this.infoService = infoService;
    }


    @GetMapping
    public ResponseEntity<Integer> getPort() {
        return ResponseEntity.ok(infoService.getPort());
    }

    @GetMapping("/someNumber")
    public ResponseEntity<Long> getNumber() {
        return ResponseEntity.ok(infoService.getNumber());
    }
}


//mvn clean install