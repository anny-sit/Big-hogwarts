package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty findByNameIgnoreCase(String name) {
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public Faculty editFaculty(Faculty faculty) {
        if (findFaculty(faculty.getId()) == null) {
            return null;
        }
        Faculty faculty1 = findFaculty(faculty.getId());
        faculty1.setName(faculty.getName());
        faculty1.setColor(faculty.getColor());

        return facultyRepository.save(faculty1);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);

    }

    public Faculty findByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }
}