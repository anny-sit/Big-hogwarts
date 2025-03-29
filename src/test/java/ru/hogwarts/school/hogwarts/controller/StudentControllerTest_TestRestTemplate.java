package ru.hogwarts.school.hogwarts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest_TestRestTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void hasStudentCreated() {
        Student student = new Student();
        student.setName("name");
        student.setAge(12);

        Student created = restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);

        assertEquals(student.getAge(), created.getAge());
        assertEquals(student.getName(), created.getName());
        assertNotNull(created.getId());

    }

    @Test
    void hasStudentFoundById() {

        Student student = new Student();
        student.setName("name");
        student.setAge(12);

        Student created = restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);

        ObjectMapper mapper = new ObjectMapper();

        Collection<LinkedHashMap> maps = restTemplate.getForEntity(
                "http://localhost:" + port + "/student?id=" + created.getId(), Collection.class).getBody();

        Collection<Student> students = maps.stream()
                .map(map -> mapper.convertValue(map, Student.class))
                .toList();

        Long foundId = Long.parseLong(
                students.stream()
                        .map(f -> String.valueOf(f.getId()))
                        .distinct()
                        .collect(Collectors.joining(""))
        );

        assertEquals(created.getId(), foundId);

    }

    @Test
    void noStudentFoundById() {

        Student student = new Student();
        student.setName("name");
        student.setAge(12);

        Student created = restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);

        ObjectMapper mapper = new ObjectMapper();

        Collection<LinkedHashMap> maps = restTemplate.getForEntity(
                "http://localhost:" + port + "/student?id=" + (created.getId() * 100), Collection.class).getBody();

        Collection<Student> students = maps.stream()
                .map(map -> mapper.convertValue(map, Student.class))
                .toList();

        assertTrue(students.isEmpty(), "Collection of students should be empty for non-existing ID");

    }

    @Test
    void hasStudentsFoundByName() {
        Student student = new Student();
        student.setName("name");
        student.setAge(12);

        Student created = restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        ObjectMapper mapper = new ObjectMapper();

        Collection<LinkedHashMap> maps = restTemplate.getForEntity(
                "http://localhost:" + port + "/student?name=" + created.getName(), Collection.class).getBody();

        Collection<Student> students = maps.stream()
                .map(map -> mapper.convertValue(map, Student.class))
                .toList();

        String foundName = students.stream()
                .map(a -> a.getName())
                .distinct()
                .collect(Collectors.joining(""));

        assertEquals(created.getName(), foundName);

    }

    @Test
    void hasStudentsFoundByAge() {
        Student student = new Student();
        student.setName("name");
        student.setAge(12);

        Student created = restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        Student student1 = new Student();
        student1.setName("nameg");
        student1.setAge(12);

        Student created1 = restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student1,
                Student.class
        );

        Student student2 = new Student();
        student2.setName("names");
        student2.setAge(12);

        Student created2 = restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student2,
                Student.class
        );

        ObjectMapper mapper = new ObjectMapper();

        Collection<LinkedHashMap> maps = restTemplate.getForEntity(
                "http://localhost:" + port + "/student?age=12", Collection.class).getBody();

        Collection<Student> students = maps.stream()
                .map(map -> mapper.convertValue(map, Student.class))
                .toList();

        List<Integer> foundAge = students.stream()
                .map(a -> a.getAge())
                .distinct()
                .collect(Collectors.toList());

        assertEquals(1, foundAge.size()); //убедиться, что все студенты одного возраста
        assertEquals(created.getAge(), foundAge.getFirst()); // убедиться, что возраст созданного студента равен искомому

    }

    @Test
    void hasStudentsFoundByAgeBetween() {
        Student student = new Student();
        student.setName("name");
        student.setAge(12);

        Student student1 = new Student();
        student1.setName("nameee");
        student1.setAge(13);

        Student student2 = new Student();
        student2.setName("nammme");
        student2.setAge(15);

        Student created = restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        Student created1 = restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student1,
                Student.class
        );

        Student created2 = restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student2,
                Student.class
        );

        ObjectMapper mapper = new ObjectMapper();

        Collection<LinkedHashMap> maps = restTemplate.getForEntity(
                "http://localhost:" + port + "/student?age=11&ageMax=14", Collection.class).getBody();

        Collection<Student> students = maps.stream()
                .map(map -> mapper.convertValue(map, Student.class))
                .toList();

        List<Integer> foundAge = students.stream()
                .map(a -> a.getAge())
                .sorted()
                .distinct()
                .collect(Collectors.toList());

        System.out.println(foundAge.getFirst());
        System.out.println(foundAge.getLast());

        assertTrue(foundAge.getFirst() > 11);
        assertTrue(foundAge.getLast() < 14);
    }

    @Test
    void hasStudentUpdated() {

        Student student = new Student();
        student.setName("student");
        student.setAge(1222);

        Student created = restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);

        Student student2 = new Student();
        student2.setId(created.getId());
        student2.setName("name2");
        student2.setAge(13);

        restTemplate.put("http://localhost:" + port + "/student", student2);

        ObjectMapper mapper = new ObjectMapper();

        Collection<LinkedHashMap> maps = restTemplate.getForEntity(
                "http://localhost:" + port + "/student?id=" + created.getId(), Collection.class).getBody();

        Collection<Student> students = maps.stream()
                .map(map -> mapper.convertValue(map, Student.class))
                .toList();

        Long foundId = Long.parseLong(
                students.stream()
                        .map(f -> String.valueOf(f.getId()))
                        .distinct()
                        .collect(Collectors.joining(""))
        );

        Integer foundAge = Integer.parseInt(
                students.stream()
                        .map(f -> String.valueOf(f.getAge()))
                        .distinct()
                        .collect(Collectors.joining(""))
        );


        String foundName = students.stream()
                .map(a -> a.getName())
                .distinct()
                .collect(Collectors.joining(""));

        assertNotNull(foundId);
        assertNotNull(foundName);
        assertNotNull(foundAge);
        assertEquals(student2.getId(), foundId);
        assertEquals(student2.getName(), foundName);
        assertEquals(student2.getAge(), foundAge);
    }

    @Test
    void hasStudentDeleted() {
        Student student = new Student();
        student.setName("names");
        student.setAge(12);

        Student created = restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);
        Long studentId = created.getId();

        restTemplate.delete("http://localhost:" + port + "/student/" + created.getId(), created.getId(), Void.class);

        ObjectMapper mapper = new ObjectMapper();

        Collection<LinkedHashMap> maps = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty?id=" + created.getId(), Collection.class).getBody();

        Collection<Faculty> faculties = maps.stream()
                .map(map -> mapper.convertValue(map, Faculty.class))
                .toList();

        assertTrue(faculties.isEmpty(), "Collection of faculties should be empty for non-existing ID");
    }
}
