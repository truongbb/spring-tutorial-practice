package org.example.studentexammanagement.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.studentexammanagement.exception.ObjectNotFoundException;
import org.example.studentexammanagement.exception.UnprocessableEntityException;
import org.example.studentexammanagement.model.request.ExamResultRequest;
import org.example.studentexammanagement.model.response.ExamResultResponse;
import org.example.studentexammanagement.service.ExamResultService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exam_results")
@AllArgsConstructor
public class ExamResultResource {

    ExamResultService examResultService;

    @PostMapping
    public ExamResultResponse createExamResult(@RequestBody @Valid ExamResultRequest request)
            throws ObjectNotFoundException, UnprocessableEntityException {
        return examResultService.createExamResult(request);
    }

}
