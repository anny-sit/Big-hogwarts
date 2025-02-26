package ru.hogwarts.school.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.findFaculty(faculty.getId());
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(facultyService.editFaculty(faculty));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFaculty(@RequestParam long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Faculty findFaculty(@RequestParam(required = false) String color,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) Long id) {
        if (color != null) {
            return facultyService.findByColor(color);
        }
        if (name != null) {
            return facultyService.findByNameIgnoreCase(name);
        }
        if (id != null) {
            return facultyService.findFaculty(id);
        }
        return (Faculty) Collections.emptyList();
    }
}
