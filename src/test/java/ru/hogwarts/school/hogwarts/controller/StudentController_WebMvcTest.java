package ru.hogwarts.school.hogwarts.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentController_WebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private StudentServiceImpl studentService;

    @Test
    public void hasStudentCreated() throws Exception {

        final String name = "name";
        final Integer age = 13;
        final Long id = 33l;

        JSONObject newStudent = new JSONObject();
        newStudent.put("name", name);
        newStudent.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(newStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void hasFacultyFoundById() throws Exception {
        final String name = "name";
        final Integer age = 13;
        final Long id = 33l;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        List<Student> studentList = List.of(student);

        when(studentRepository.findAll()).thenReturn(studentList);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.empty());


        mockMvc.perform(MockMvcRequestBuilders.get("/student?id=" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.intValue()))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));

    }

    @Test
    void hasFacultyFoundByName() throws Exception {
        final String name = "name";
        final Integer age = 13;
        final Long id = 33l;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        List<Student> studentList = List.of(student);

        when(studentRepository.findAll()).thenReturn(studentList);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.empty());


        mockMvc.perform(MockMvcRequestBuilders.get("/student?name=" + name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.intValue()))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));

    }

    @Test
    void hasFacultyFoundByAge() throws Exception {
        final String name = "name";
        final Integer age = 13;
        final Long id = 33l;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        List<Student> studentList = List.of(student);

        when(studentRepository.findAll()).thenReturn(studentList);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.empty());


        mockMvc.perform(MockMvcRequestBuilders.get("/student?age=" + age)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.intValue()))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));

    }

    @Test
    void hasFacultyUpdated() throws Exception {
        final String name = "name";
        final Integer age = 13;
        final Long id = 33l;

        JSONObject newStudent = new JSONObject();
        newStudent.put("id", id);
        newStudent.put("name", name);
        newStudent.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.empty());


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(newStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }

    @Test
    void shouldDeleteFacultyById() throws Exception {
        final Long id = 2252L;

        doNothing().when(studentRepository).deleteById(any(Long.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(studentRepository).deleteById(id);
    }

}
