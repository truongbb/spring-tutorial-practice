package org.example.studentexammanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.studentexammanagement.dto.SearchStudentDto;
import org.example.studentexammanagement.entity.Student;
import org.example.studentexammanagement.exception.ObjectNotFoundException;
import org.example.studentexammanagement.model.request.SearchStudentRequest;
import org.example.studentexammanagement.model.request.StudentRequest;
import org.example.studentexammanagement.model.response.CommonSearchResponse;
import org.example.studentexammanagement.model.response.StudentResponse;
import org.example.studentexammanagement.repository.StudentRepository;
import org.example.studentexammanagement.repository.custom.StudentCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    ObjectMapper objectMapper;

    StudentRepository studentRepository;

    StudentCustomRepository studentCustomRepository;

    public CommonSearchResponse<StudentResponse> searchStudent(SearchStudentRequest request) {
        Pageable pageable = PageRequest.of(request.getPageIndex(), request.getPageSize());

        Page<Student> studentPages = studentRepository.findByNameContainingIgnoreCase(request.getName(), pageable);

        List<StudentResponse> studentResponses = studentPages
                .map(s -> objectMapper.convertValue(s, StudentResponse.class))
                .toList();

        return CommonSearchResponse.<StudentResponse>builder()
                .totalRecord(studentPages.getTotalElements())
                .totalPage(studentPages.getTotalPages())
                .data(studentResponses)
                .paging(new CommonSearchResponse.CommonSearchPageInfo(request.getPageSize(), request.getPageIndex()))
                .build();
    }

    public StudentResponse getStudentById(Long id) throws ObjectNotFoundException {
        return studentRepository.findById(id)
                .map(s -> objectMapper.convertValue(s, StudentResponse.class))
                .orElseThrow(() -> new ObjectNotFoundException("Student not found"));
    }

    public StudentResponse createStudent(StudentRequest request) {
        Student student = objectMapper.convertValue(request, Student.class);
        student.setGpa(0f);
        student = studentRepository.save(student);
        return objectMapper.convertValue(student, StudentResponse.class);
    }

    public CommonSearchResponse<StudentResponse> searchStudentVer2(SearchStudentRequest request) {
        List<SearchStudentDto> result = studentCustomRepository.searchStudent(request);
        Long totalRecord = 0l;
        List<StudentResponse> studentResponses = new ArrayList<>();
        if (!result.isEmpty()) {
            totalRecord = result.get(0).getTotalRecord();
            studentResponses = result
                    .stream()
                    .map(s -> objectMapper.convertValue(s, StudentResponse.class))
                    .toList();
        }

        int totalPage = (int) Math.ceil((double) totalRecord / request.getPageSize());

        return CommonSearchResponse.<StudentResponse>builder()
                .totalRecord(totalRecord)
                .totalPage(totalPage)
                .data(studentResponses)
                .paging(new CommonSearchResponse.CommonSearchPageInfo(request.getPageSize(), request.getPageIndex()))
                .build();
    }
}
