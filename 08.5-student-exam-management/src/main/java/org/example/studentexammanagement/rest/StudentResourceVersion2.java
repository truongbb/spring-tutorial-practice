package org.example.studentexammanagement.rest;

import lombok.AllArgsConstructor;
import org.example.studentexammanagement.model.request.SearchStudentRequest;
import org.example.studentexammanagement.model.response.CommonSearchResponse;
import org.example.studentexammanagement.model.response.StudentResponse;
import org.example.studentexammanagement.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/students")
public class StudentResourceVersion2 {

    StudentService studentService;

    @GetMapping
    public CommonSearchResponse<StudentResponse> searchStudent(SearchStudentRequest request) {
        return studentService.searchStudentVer2(request);
    }

}
