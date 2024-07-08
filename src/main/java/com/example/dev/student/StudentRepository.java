package com.example.dev.student;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {

    private static final Logger log = LoggerFactory.getLogger(StudentRepository.class);

    private final JdbcClient jdbcClient;

    public StudentRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Student> getAll() {
        return jdbcClient.sql("select * from student")
                .query(Student.class)
                .list();
    }

    public Optional<Student> getById(int id) {
        return jdbcClient.sql("select * from Student where id = :id")
                .query(Student.class)
                .optional();
    }

    public void add(Student student) {
        var update = jdbcClient.sql("insert into Student (name,age) values(?,?)")
                .params(List.of(student.name(), student.age()))
                .update();
        Assert.state(update == 1, "Studente non inserito!" + student.name());
    }

    public void update(int id, Student student) {
        int update = jdbcClient.sql("update Student set name=?,age=? where id=?")
                .params(List.of(student.name(), student.age(), id))
                .update();
        Assert.state(update == 1, "Dati dello Studente: " + student.name() + " non aggiornati!");
    }

    public void delete(int id) {
        int update = jdbcClient.sql("delete from Student where id=?")
                .params(List.of(id))
                .update();
        Assert.state(update == 1, "Studente: " + id + " non eliminato!");
    }

    public int count() {
        return jdbcClient.sql("select count(*) from Student")
                .query()
                .listOfRows()
                .size();
    }

    public void saveAll(List<Student> students) {
        students.stream().forEach(this::add);

    }
}
