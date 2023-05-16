package com.github.truongbb.tinystudentmanagement01.service;

import com.github.truongbb.tinystudentmanagement01.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>();

    private static int AUTO_ID = 1;

    public List<Student> getAlStudents() {
        return students;
    }

    public void saveStudent(Student student) {
        student.setId(AUTO_ID);
        students.add(student);
        AUTO_ID++;
    }

    public void delete(String id) {
        students.removeIf(s -> s.getId() == Integer.parseInt(id));
//        students.stream().filter(s -> s  .getId() != id).collect(Collectors.toList());
    }

    public Student findById(String id) {
        return students
                .stream()
                .filter(s -> s.getId() == Integer.parseInt(id))
                .findFirst()
                .get();
    }

    public void updateStudent(Student student) {
        students.forEach(s -> {
            if (s.getId() != student.getId()) {
                return;
            }
            s.setName(student.getName());
            s.setAddress(student.getAddress());
            s.setDob(student.getDob());
            s.setGpa(student.getGpa());
        });
    }
}
