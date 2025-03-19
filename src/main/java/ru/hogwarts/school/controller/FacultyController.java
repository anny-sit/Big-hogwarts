package ru.hogwarts.school.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.Collections;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyServiceImpl facultyServiceImpl;

    public FacultyController(FacultyServiceImpl facultyServiceImpl) {
        this.facultyServiceImpl = facultyServiceImpl;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyServiceImpl.addFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyServiceImpl.findFaculty(faculty.getId());
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(facultyServiceImpl.editFaculty(faculty));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFaculty(@RequestParam long id) {
        facultyServiceImpl.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Faculty findFaculty(@RequestParam(required = false) String color,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) Long id) {
        if (color != null) {
            return facultyServiceImpl.findByColor(color);
        }
        if (name != null) {
            return facultyServiceImpl.findByNameIgnoreCase(name);
        }
        if (id != null) {
            return facultyServiceImpl.findFaculty(id);
        }
        return (Faculty) Collections.emptyList();
    }
}
