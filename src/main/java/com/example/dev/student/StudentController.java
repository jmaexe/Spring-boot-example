package com.example.dev.student;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("")
    public List<Student> getAllStudents() {
        return studentRepository.getAll();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        Optional<Student> studentById = studentRepository.getById(id);
        if(studentById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Student not found");
        }
        return studentById.get();

    }

    @ResponseStatus(HttpStatus.CREATED) //201
    @PostMapping("/add")
    public void addStudent(@Valid @RequestBody Student student) {
        studentRepository.add(student);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateStudent(@PathVariable int id,@Valid @RequestBody Student student ) {
        studentRepository.update(id,student);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable int id) {
        studentRepository.delete(id);
    }


}

