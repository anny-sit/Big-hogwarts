package ru.hogwarts.school.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.StudentSearchCriteria;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentServiceImpl studentServiceImpl;

    public StudentController(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentServiceImpl.addStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {

        Student foundStudent = studentServiceImpl.findStudent(student.getId());
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(studentServiceImpl.editStudent(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentServiceImpl.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam(required = false) Integer age,
                                                            @RequestParam(required = false) Integer ageMax,
                                                            @RequestParam(required = false) String name,
                                                            @RequestParam(required = false) Long id) {

        StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria(name, age, ageMax, id);

        return ResponseEntity.ok(studentServiceImpl.getAllStudents(studentSearchCriteria));
    }


    @GetMapping("/last5")
    public ResponseEntity getLastFiveStudents() {
        return ResponseEntity.ok(studentServiceImpl.getLastFiveStudents());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount() {
        Integer count = studentServiceImpl.getCountOfStudents();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/avg-age")
    public ResponseEntity<Map<String, Double>> getAvgAge() {
        Double avgAge = studentServiceImpl.getAvgAgeOfStudents().doubleValue();
        return ResponseEntity.ok(Collections.singletonMap("averageAge", avgAge));
    }

    @GetMapping("/letter")
    public ResponseEntity<Collection<String>> getAllStudentsStartedWithLetter(@RequestParam String letter) {
        return ResponseEntity.ok(studentServiceImpl.getNamesStartedWithLetter(letter));
    }

    @GetMapping("/print-parallel")
    public ResponseEntity<String> printNamesParallel() {
        List<String> incoming = studentServiceImpl.getNamesCollectionForThreads(6);

        incoming.stream().forEach(System.out::println);

        System.out.println("First name: " + incoming.get(0));
        System.out.println("Second name: " + incoming.get(1));

        new Thread(() -> {
            System.out.println("Third name: " + incoming.get(2));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Fourth name: " + incoming.get(3));
        }).start();

        new Thread(() -> {
            System.out.println("Fifth name: " + incoming.get(4));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Sixth name: " + incoming.get(5));
        }).start();

        return ResponseEntity.ok("The result is displayed in the console");

    }


    @GetMapping("/print-synchronized")
    public ResponseEntity<String> printNamesSynchronized() {
        List<String> incoming = studentServiceImpl.getNamesCollectionForThreads(6);

        incoming.stream().forEach(System.out::println);

        System.out.println(studentServiceImpl.printNameWithNumberSync(incoming.get(0)));
        System.out.println(studentServiceImpl.printNameWithNumberSync(incoming.get(1)));

        new Thread(() -> {
            System.out.println(studentServiceImpl.printNameWithNumberSync(incoming.get(2)));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(studentServiceImpl.printNameWithNumberSync(incoming.get(3)));
        }).start();

        new Thread(() -> {
            System.out.println(studentServiceImpl.printNameWithNumberSync(incoming.get(4)));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(studentServiceImpl.printNameWithNumberSync(incoming.get(5)));
        }).start();

        return ResponseEntity.ok("The result is displayed in the console");
    }

}