package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

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

    @GetMapping("/someNumber")
    public Long getNumber() {
        long sum = Stream.iterate(1l, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0l, (Long::sum));

        return sum;
    }
}


//mvn clean install