package ru.hogwarts.school.hogwarts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest_TestRestTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void isControllerPresent() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void hasFacultyCreated() {
        Faculty faculty = new Faculty();
        faculty.setColor("red");
        faculty.setName("faculty");

        Faculty created = restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        assertEquals(faculty.getColor(), created.getColor());
        assertEquals(faculty.getName(), faculty.getName());
        assertNotNull(created.getId());

    }

    @Test
    void hasFacultyFoundById() {

        Faculty faculty = new Faculty();
        faculty.setColor("green");
        faculty.setName("faculty");

        Faculty created = restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        ObjectMapper mapper = new ObjectMapper();

        Collection<LinkedHashMap> maps = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty?id=" + created.getId(), Collection.class).getBody();

        Collection<Faculty> faculties = maps.stream()
                .map(map -> mapper.convertValue(map, Faculty.class))
                .toList();

        Long foundId = Long.parseLong(
                faculties.stream()
                        .map(f -> String.valueOf(f.getId()))
                        .distinct()
                        .collect(Collectors.joining(""))
        );

        assertNotNull(foundId);
        assertEquals(created.getId(), foundId);

    }

    @Test
    void noFacultyFoundById() {

        Faculty faculty = new Faculty();
        faculty.setColor("green");
        faculty.setName("faculty");

        Faculty created = restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        ObjectMapper mapper = new ObjectMapper();

        Collection<LinkedHashMap> maps = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty?id=" + (created.getId() + 10000), Collection.class).getBody();

        Collection<Faculty> faculties = maps.stream()
                .map(map -> mapper.convertValue(map, Faculty.class))
                .toList();

        assertTrue(faculties.isEmpty(), "Collection of faculties should be empty for non-existing ID");

    }

    @Test
    void hasFacultyFoundByName() {
        Faculty faculty = new Faculty();
        faculty.setColor("red");
        faculty.setName("string");

        Faculty created = restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        ObjectMapper mapper = new ObjectMapper();

        Collection<LinkedHashMap> maps = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty?name=" + created.getName(), Collection.class).getBody();

        Collection<Faculty> faculties = maps.stream()
                .map(map -> mapper.convertValue(map, Faculty.class))
                .toList();

        String foundName = faculties.stream()
                .map(a -> a.getName())
                .distinct()
                .collect(Collectors.joining(""));

        assertEquals(created.getName(), foundName);

    }

    @Test
    void hasFacultyFoundByColour() {
        Faculty faculty = new Faculty();
        faculty.setColor("red");
        faculty.setName("string");

        Faculty created = restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        ObjectMapper mapper = new ObjectMapper();

        Collection<LinkedHashMap> maps = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty?color=" + created.getColor(), Collection.class).getBody();

        Collection<Faculty> faculties = maps.stream()
                .map(map -> mapper.convertValue(map, Faculty.class))
                .toList();

        String foundColour = faculties.stream()
                .map(a -> a.getColor())
                .distinct()
                .collect(Collectors.joining(""));

        assertEquals(created.getColor(), foundColour);

    }

    @Test
    void hasFacultyUpdated() {

        Faculty faculty = new Faculty();
        faculty.setColor("green");
        faculty.setName("faculty");

        Faculty created = restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        Faculty faculty1 = new Faculty();
        faculty1.setId(created.getId());
        faculty1.setColor("greened");
        faculty1.setName("faculties");

        restTemplate.put("http://localhost:" + port + "/faculty", faculty1);

        ObjectMapper mapper = new ObjectMapper();

        Collection<LinkedHashMap> maps = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty?id=" + created.getId(), Collection.class).getBody();

        Collection<Faculty> faculties = maps.stream()
                .map(map -> mapper.convertValue(map, Faculty.class))
                .toList();

        Long foundId = Long.parseLong(
                faculties.stream()
                        .map(f -> String.valueOf(f.getId()))
                        .distinct()
                        .collect(Collectors.joining(""))
        );

        String foundColour = faculties.stream()
                .map(a -> a.getColor())
                .distinct()
                .collect(Collectors.joining(""));


        String foundName = faculties.stream()
                .map(a -> a.getName())
                .distinct()
                .collect(Collectors.joining(""));

        assertNotNull(foundId);
        assertNotNull(foundName);
        assertNotNull(foundColour);
        assertEquals(faculty1.getId(), foundId);
        assertEquals(faculty1.getName(), foundName);
        assertEquals(faculty1.getColor(), foundColour);

    }

    @Test
    void hasFacultyDeleted() {
        Faculty faculty = new Faculty();
        faculty.setColor("green");
        faculty.setName("faculty");

        Faculty created = restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        Long facultyId = created.getId();

        restTemplate.delete("http://localhost:" + port + "/faculty/" + facultyId);

        ObjectMapper mapper = new ObjectMapper();

        Collection<LinkedHashMap> maps = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty?id=" + created.getId(), Collection.class).getBody();

        Collection<Faculty> faculties = maps.stream()
                .map(map -> mapper.convertValue(map, Faculty.class))
                .toList();

        assertTrue(faculties.isEmpty(), "Collection of faculties should be empty for non-existing ID");
    }
}
