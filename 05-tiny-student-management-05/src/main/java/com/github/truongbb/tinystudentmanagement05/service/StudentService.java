package com.github.truongbb.tinystudentmanagement05.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.truongbb.tinystudentmanagement05.entity.Student;
import com.github.truongbb.tinystudentmanagement05.model.request.StudentCreateRequest;
import com.github.truongbb.tinystudentmanagement05.model.request.StudentUpdateRequest;
import com.github.truongbb.tinystudentmanagement05.model.response.StudentResponse;
import com.github.truongbb.tinystudentmanagement05.statics.Region;
import com.github.truongbb.tinystudentmanagement05.validation.ObjectNotFoundException;
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

    public void saveStudent(StudentCreateRequest studentCreateRequest) {
        Student student = objectMapper.convertValue(studentCreateRequest, Student.class);
        student.setId(AUTO_ID);
        students.add(student);
        AUTO_ID++;
    }

    public void delete(String id) {
        students.removeIf(s -> s.getId() == Integer.parseInt(id));
//        students.stream().filter(s -> s  .getId() != id).collect(Collectors.toList());
    }

    public StudentUpdateRequest findById(String id) {
        Optional<Student> studentOptional = students
                .stream()
                .filter(s -> s.getId() == Integer.parseInt(id))
                .findFirst();
        if (studentOptional.isEmpty()) {
            throw new ObjectNotFoundException("Không tìm thấy sinh viên mang mã " + id);
        }
        Student student = studentOptional.get();
        return objectMapper.convertValue(student, StudentUpdateRequest.class);
    }

    public void updateStudent(StudentUpdateRequest studentUpdateRequest) {
        students.forEach(s -> {
            if (s.getId() != studentUpdateRequest.getId()) {
                return;
            }
            s.setName(studentUpdateRequest.getName());
            s.setAddress(studentUpdateRequest.getAddress());
            s.setEmail(studentUpdateRequest.getEmail());
            s.setDob(studentUpdateRequest.getDob());
            s.setGpa(studentUpdateRequest.getGpa());
            s.setRegion(Region.valueOf(studentUpdateRequest.getRegion()));
        });
    }

    public StudentResponse findByIdVer2(Integer id) {
        Optional<Student> studentOptional = students
                .stream()
                .filter(s -> s.getId() == id)
                .findFirst();
        if (studentOptional.isEmpty()) {
            throw new ObjectNotFoundException("Không tìm thấy sinh viên mang mã " + id);
        }
        Student student = studentOptional.get();
        return objectMapper.convertValue(student, StudentResponse.class);
    }

}
