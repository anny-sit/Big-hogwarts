package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "select count(name) from \"public\".student", nativeQuery = true)
    Integer getCountOfStudents();

    @Query(value = "select avg(age) from \"public\".student", nativeQuery = true)
    Double getAvgAgeOfStudents();

    @Query(value = "select * from \"public\".student order by id desc limit 5", nativeQuery = true)
    List<GetLastFiveStudents> getLastFiveStudents();
}
