package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(Integer age);

    Student findByName(String name);

    Collection<Student> findByFaculty_id(Integer faculty_id);

    Collection<Student> findByAgeBetween(Integer min, Integer max);
}
