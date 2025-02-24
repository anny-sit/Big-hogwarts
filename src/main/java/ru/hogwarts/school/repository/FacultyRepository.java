package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Faculty findByName(String faculty);
    Faculty findByNameIgnoreCase(String faculty);
    Faculty findByColorIgnoreCase(String color);
}
