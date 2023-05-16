package com.github.truongbb.tinystudentmanagement02.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.truongbb.tinystudentmanagement02.entity.Student;
import com.github.truongbb.tinystudentmanagement02.model.StudentModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentService {

    ObjectMapper objectMapper;

    List<Student> students;

    private static int AUTO_ID = 1;

    public List<Student> getAlStudents() {
        return students;
    }

    public void saveStudent(StudentModel studentModel) {
        Student student = objectMapper.convertValue(studentModel, Student.class);
        student.setId(AUTO_ID);
        students.add(student);
        AUTO_ID++;
    }

    public void delete(String id) {
        students.removeIf(s -> s.getId() == Integer.parseInt(id));
//        students.stream().filter(s -> s  .getId() != id).collect(Collectors.toList());
    }

    public StudentModel findById(String id) {
        Optional<Student> studentOptional = students
                .stream()
                .filter(s -> s.getId() == Integer.parseInt(id))
                .findFirst();
        if (studentOptional.isEmpty()) {
            return null;
        }
        Student student = studentOptional.get();
        return objectMapper.convertValue(student, StudentModel.class);

    }

    public void updateStudent(StudentModel studentModel) {
        students.forEach(s -> {
            if (s.getId() != studentModel.getId()) {
                return;
            }
            s.setName(studentModel.getName());
            s.setAddress(studentModel.getAddress());
            s.setDob(studentModel.getDob());
            s.setGpa(studentModel.getGpa());
        });
    }

}
