package org.example.studentexammanagement.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.studentexammanagement.exception.ObjectNotFoundException;
import org.example.studentexammanagement.model.request.SearchStudentRequest;
import org.example.studentexammanagement.model.request.StudentRequest;
import org.example.studentexammanagement.model.response.CommonSearchResponse;
import org.example.studentexammanagement.model.response.StudentResponse;
import org.example.studentexammanagement.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentResource {

    StudentService studentService;

//    @GetMapping
//    public List<StudentResponse> searchStudent(
//            @RequestParam(value = "ho_va_ten", required = false) String name,
//            @RequestParam(value = "pageSize") Integer pageSize,
//            @RequestParam(value = "pageIndex") Integer pageIndex
//    ) {
//        return studentService.searchStudent(name);
//    }

    @GetMapping
    public CommonSearchResponse<StudentResponse> searchStudent(SearchStudentRequest request) {
        return studentService.searchStudent(request);
    }

    @GetMapping("/{id}")
    public StudentResponse getStudentById(@PathVariable Long id) throws ObjectNotFoundException {
        return studentService.getStudentById(id);
    }

    @PostMapping
    public StudentResponse createStudent(@RequestBody @Valid StudentRequest request) {
        return studentService.createStudent(request);
    }


}
