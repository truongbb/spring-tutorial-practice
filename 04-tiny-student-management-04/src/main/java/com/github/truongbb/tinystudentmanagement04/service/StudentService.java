package com.github.truongbb.tinystudentmanagement03.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.truongbb.tinystudentmanagement03.entity.Student;
import com.github.truongbb.tinystudentmanagement03.exceptionhandling.exception.ObjectNotFoundException;
import com.github.truongbb.tinystudentmanagement03.model.StudentCreateModel;
import com.github.truongbb.tinystudentmanagement03.model.StudentUpdateModel;
import com.github.truongbb.tinystudentmanagement03.statics.Region;
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

    public void saveStudent(StudentCreateModel studentCreateModel) {
        Student student = objectMapper.convertValue(studentCreateModel, Student.class);
        student.setId(AUTO_ID);
        students.add(student);
        AUTO_ID++;
    }

    public void delete(String id) {
        students.removeIf(s -> s.getId() == Integer.parseInt(id));
//        students.stream().filter(s -> s  .getId() != id).collect(Collectors.toList());
    }

    public StudentUpdateModel findById(String id) {
        Optional<Student> studentOptional = students
                .stream()
                .filter(s -> s.getId() == Integer.parseInt(id))
                .findFirst();
        if (studentOptional.isEmpty()) {
            throw new ObjectNotFoundException("Không tìm thấy sinh viên mang mã " + id);
        }
        Student student = studentOptional.get();
        return objectMapper.convertValue(student, StudentUpdateModel.class);

    }

    public void updateStudent(StudentUpdateModel studentUpdateModel) {
        students.forEach(s -> {
            if (s.getId() != studentUpdateModel.getId()) {
                return;
            }
            s.setName(studentUpdateModel.getName());
            s.setAddress(studentUpdateModel.getAddress());
            s.setEmail(studentUpdateModel.getEmail());
            s.setDob(studentUpdateModel.getDob());
            s.setGpa(studentUpdateModel.getGpa());
            s.setRegion(Region.valueOf(studentUpdateModel.getRegion()));
        });
    }

}
