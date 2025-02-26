package ru.hogwarts.school.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }
    public Student findStudentByName(String name) {
        return studentRepository.findByName(name);
    }

    public Student editStudent(Student student) {
        if (findStudent(student.getId()) == null) {
            return null;
        }
        Student student1 = (Student) findStudent(student.getId()); // если один или несколько параметров null? дописать проверки
        student1.setName(student.getName());
        student1.setAge(student.getAge());
        student1.setFaculty(student.getFaculty());
        return studentRepository.save(student1);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(Integer age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(Integer min, Integer max) {
        return studentRepository.findByAgeBetween(min, max);
    }


}
