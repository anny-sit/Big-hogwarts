package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class InfoServiceImpl implements InfoService {

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    public Integer getPort() {
        int port = webServerAppCtxt.getWebServer().getPort();
        return port;
    }

    public Long getNumber() {
        logger.debug("метод вызван" + System. currentTimeMillis());
        long sum = Stream.iterate(1l, a -> a + 1)
                .limit(1_000_000)
                //.parallel()
                .reduce(0l, (Long::sum));
        logger.debug("метод закончил работу" + System. currentTimeMillis());
        return sum;
    }
}
