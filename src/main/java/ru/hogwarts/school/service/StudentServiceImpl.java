package ru.hogwarts.school.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.StudentSearchCriteria;
import ru.hogwarts.school.repository.GetLastFiveStudents;
import ru.hogwarts.school.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    //private final FacultyServiceImpl facultyService;

    public StudentServiceImpl(StudentRepository studentRepository/*, FacultyServiceImpl facultyService*/) {
        this.studentRepository = studentRepository;
        //this.facultyService = facultyService;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        return studentRepository.findById(id)
                .orElse(null);
    }


    public Student editStudent(Student student) {

        if (findStudent(student.getId()) == null) {
            return null;
        }

        Student student1 = findStudent(student.getId());
        student1.setName(student.getName());
        student1.setAge(student.getAge());

        /*if (facultyService.findFaculty(student.getFaculty().getId()) == null && student.getFaculty() != null) {
            student1.setFaculty(facultyService.addFaculty(student.getFaculty()));
        } else {
            student1.setFaculty(student.getFaculty());
        }*/

        return studentRepository.save(student1);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents(StudentSearchCriteria searchCriteria) {

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
        return studentRepository.getCountOfStudents();
    }

    public Double getAvgAgeOfStudents() {
        return studentRepository.getAvgAgeOfStudents();
    }

    public List<GetLastFiveStudents> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }

}