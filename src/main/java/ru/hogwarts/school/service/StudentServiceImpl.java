package ru.hogwarts.school.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.StudentSearchCriteria;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.GetLastFiveStudents;
import ru.hogwarts.school.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    final Object flag = new Object();
    int counter = 0;

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student addStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        logger.info("Was invoked method for finding student");
        return studentRepository.findById(id)
                .orElse(null);
    }


    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student");

        if (findStudent(student.getId()) == null) {
            logger.error("No such student ID");
            return null;
        }

        Student student1 = findStudent(student.getId());
        student1.setName(student.getName());
        student1.setAge(student.getAge());

        if (!facultyRepository.existsById(student.getFaculty().getId()) && student.getFaculty() != null) {
            logger.debug("No such faculty or faculty is empty");
            student1.setFaculty(facultyRepository.save(student.getFaculty()));
        } else {
            student1.setFaculty(student.getFaculty());
        }

        return studentRepository.save(student1);
    }

    public void deleteStudent(long id) {
        logger.info("Was invoked method for delete student");
        if (studentRepository.findById(id) == null) {
            logger.debug("Can't delete absent student");
        }
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents(StudentSearchCriteria searchCriteria) {
        logger.info("Was invoked method for getting all students with criteria");

        if (searchCriteria.ageMax() != null) {
            return studentRepository.findAll()
                    .stream()
                    .filter(a -> Optional.ofNullable(searchCriteria.id())
                            .map(c -> c.equals(a.getId())).orElse(true))
                    .filter(a -> Optional.ofNullable(searchCriteria.name())
                            .map(c -> c.equals(a.getName())).orElse(true))
                    .filter(a -> Optional.ofNullable(searchCriteria.ageMax())
                            .map(c -> c >= a.getAge()).orElse(true))
                    .filter(a -> Optional.ofNullable(searchCriteria.age())
                            .map(c -> c <= a.getAge()).orElse(true))
                    .toList();
        }

        return studentRepository.findAll()
                .stream()
                .filter(a -> Optional.ofNullable(searchCriteria.id())
                        .map(c -> c.equals(a.getId())).orElse(true))
                .filter(a -> Optional.ofNullable(searchCriteria.name())
                        .map(c -> c.equals(a.getName())).orElse(true))
                .filter(a -> Optional.ofNullable(searchCriteria.age())
                        .map(c -> c.equals(a.getAge())).orElse(true))
                .toList();

    }

    public Integer getCountOfStudents() {
        logger.info("Was invoked method for counting students");
        return studentRepository.getCountOfStudents();
    }

    public Double getAvgAgeOfStudents() {
        logger.info("Was invoked method for getting avg age of students");
        return studentRepository.findAll()
                .stream()
                .parallel()
                .map(a -> a.getAge())
                .collect(Collectors.averagingDouble(Double::valueOf));
    }

    public List<GetLastFiveStudents> getLastFiveStudents() {
        logger.info("Was invoked method for getting last 5 students");
        return studentRepository.getLastFiveStudents();
    }

    public List<String> getNamesStartedWithLetter(String letter) {
        return studentRepository.findAll()
                .stream()
                .parallel()
                .map(a -> a.getName())
                .filter(a -> a.startsWith(letter))
                .toList();
    }

    public List<String> getNamesCollectionForThreads(Integer count) {
        return studentRepository.findAll()
                .stream()
                .map(a -> a.getName())
                .limit(count)
                .toList();
    }

    public String printNameWithNumberSync(String name) {
        String toReturn;
        synchronized (flag) {
            toReturn = "Name " + counter + " is " + name;
            counter++;
        }
        return toReturn;
    }

}