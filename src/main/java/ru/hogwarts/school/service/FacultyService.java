package ru.hogwarts.school.service;

import java.util.*;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        return facultyRepository.getById(id);
    }

    public Faculty findByNameIgnoreCase(String name) { return facultyRepository.findByNameIgnoreCase(name);}

    public Faculty editFaculty(Faculty faculty) {

        if (facultyRepository.findByName(faculty.getName()) == null) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
    public Faculty findByColor(String color) {

        return facultyRepository.findByColorIgnoreCase(color);
    }
}