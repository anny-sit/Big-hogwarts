package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.FacultySearchCriteria;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");

        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        logger.info("Was invoked method for find faculty by id");

        return facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }


    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for edit faculty");

        if (findFaculty(faculty.getId()) == null) {
            logger.warn("can't find faculty");
            return null;
        }

        Faculty faculty1 = findFaculty(faculty.getId());
        faculty1.setName(faculty.getName());
        faculty1.setColor(faculty.getColor());

        return facultyRepository.save(faculty1);
    }

    public void deleteFaculty(long id) {
        logger.info("Was invoked method for delete faculty");
        if (facultyRepository.findById(id) == null) {
            logger.warn("Can't delete absent faculty");
        }
        facultyRepository.deleteById(id);

    }

    public Collection<Faculty> getAllFaculties(FacultySearchCriteria criteria) {
        logger.info("Was invoked method for getting all faculties by criteria");

        return facultyRepository.findAll()
                .stream()
                .filter(a -> Optional.ofNullable(criteria.id()).map(c -> c.equals(a.getId())).orElse(true))
                .filter(a -> Optional.ofNullable(criteria.color()).map(c -> c.equals(a.getColor())).orElse(true))
                .filter(b -> Optional.ofNullable(criteria.name()).map(c -> c.equals(b.getName())).orElse(true))
                .toList();
    }

    public String getTheLongestFacultyName() {
        return facultyRepository.findAll()
                .stream()
                .parallel()
                .max(Comparator.comparingInt(faculty -> faculty.getName().length()))
                .map(Faculty::getName)
                .orElse("");
    }
}