package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.FacultySearchCritera;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Optional;

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
        return facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
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

    public Collection<Faculty> getAllFaculties(FacultySearchCritera critera) {
        return facultyRepository.findAll()
                .stream()
                .filter(a -> Optional.ofNullable(critera.id()).map(c -> c.equals(a.getId())).orElse(true))
                .filter(a -> Optional.ofNullable(critera.color()).map(c -> c.equals(a.getColor())).orElse(true))
                .filter(b -> Optional.ofNullable(critera.name()).map(c -> c.equals(b.getName())).orElse(true))
                .toList();
    }
}