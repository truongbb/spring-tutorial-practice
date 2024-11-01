package org.example.studentexammanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.studentexammanagement.entity.ExamResult;
import org.example.studentexammanagement.entity.Student;
import org.example.studentexammanagement.entity.Subject;
import org.example.studentexammanagement.exception.ObjectNotFoundException;
import org.example.studentexammanagement.exception.UnprocessableEntityException;
import org.example.studentexammanagement.model.request.ExamResultRequest;
import org.example.studentexammanagement.model.response.ExamResultResponse;
import org.example.studentexammanagement.model.response.StudentResponse;
import org.example.studentexammanagement.model.response.SubjectResponse;
import org.example.studentexammanagement.repository.custom.ExamResultCustomRepository;
import org.example.studentexammanagement.repository.ExamResultRepository;
import org.example.studentexammanagement.repository.StudentRepository;
import org.example.studentexammanagement.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
//@Transactional
public class ExamResultService {

    ObjectMapper objectMapper;
    StudentRepository studentRepository;
    SubjectRepository subjectRepository;
    ExamResultRepository examResultRepository;
    ExamResultCustomRepository examResultCustomRepository;

    @Transactional
    public ExamResultResponse createExamResult(ExamResultRequest request)
            throws ObjectNotFoundException, UnprocessableEntityException {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ObjectNotFoundException("Student not found"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ObjectNotFoundException("Subject not found"));

        // check lần thi
        // 1 - tìm xem trong db bảng exam_results xem da có sinh vin này thi môn này hay chua
        // --> 1 list
        List<ExamResult> exams = examResultRepository.findByStudentIdAndSubjectId(
                request.getStudentId(), request.getSubjectId()
        );

        // xem xem cái list này có bao nhiêu phần tử
        // nếu da có 3 rồi thì không cho thi nữa
        if (exams.size() >= 3) {
            throw new UnprocessableEntityException("Student can not take this exam anymore (>3)");
        }

        ExamResult examResult = ExamResult.builder()
                .student(student)
                .subject(subject)
                .mark(request.getMark())
                .examDate(request.getExamDate())
                .examTimes(exams.size() + 1)
                .build();
        examResult = examResultRepository.save(examResult);

//        float gpa = calculateStudentGpa(request.getStudentId());
        Double gpaModel = examResultCustomRepository.calculateStudentGpa(student.getId());
        student.setGpa(gpaModel.floatValue());
        studentRepository.save(student);

        String temp = "\\%";

        return ExamResultResponse.builder()
                .id(examResult.getId())
                .student(objectMapper.convertValue(student, StudentResponse.class))
                .subject(objectMapper.convertValue(subject, SubjectResponse.class))
                .mark(examResult.getMark())
                .examDate(examResult.getExamDate())
                .examTimes(examResult.getExamTimes())
                .build();
    }

    /**
     * điểm thi -> lấy lần thi cao nhất
     * gpa = Tổng (điểm thi * số tín chỉ) / tổng số tín chỉ
     * <p>
     * 1. lấy tất cả kết quả của sinh viên này
     * <p>
     * 2. tu danh sách trn -> lọc ra 1 danh sách chứa điểm thi chính thức cua SV
     * <p>
     * 3. Tính theo công thức
     */
    public float calculateStudentGpa(Long studentId) {
        List<ExamResult> exams = examResultRepository.findByStudentId(studentId);
        /**
         * SV nguyễn văn A:
         * - Giải tich : (1, 5), (2, 7), (3, 8)
         * - Dai so: (1, 9)
         * - Triet: (1, 7)
         * - Tin hoc van phong: (1, 4), (2, 9)
         *
         *
         * SV    MH   diem    lan_thi
         * 1     1     5        1
         * 1     1     7        2
         * 1     1     8        3
         *
         * 1     2     9        1
         *
         * 1     3     7        1
         *
         * 1     4     4        1
         * 1     4     9        2
         */

        /**
         * SV nguyễn văn A:
         * - Giải tich : 8
         * - Dai so: 9
         * - Triet: 7
         * - Tin hoc van phong: 9
         */

//        Set<Long> subjectIds = exams
//                .stream()
//                .map(ExamResult::getSubject)
//                .map(Subject::getId)
//                .collect(Collectors.toSet());
        Set<Long> subjectIds = new HashSet<>();
        for (int i = 0; i < exams.size(); i++) {
            subjectIds.add(exams.get(i).getSubject().getId());
        }

        List<ExamResult> finalResult = new ArrayList<>();
        subjectIds.forEach(subjectId -> {
            List<ExamResult> tempExams = exams
                    .stream()
                    .filter(e -> Objects.equals(e.getSubject().getId(), subjectId))
                    .toList();

            // lay ra lan thi co diem cao nhat
            ExamResult max = tempExams.get(0);
            if (tempExams.size() == 1) {
                finalResult.add(max);
                return;
            }
            for (int i = 1; i < tempExams.size(); i++) {
                if (tempExams.get(i).getMark() > max.getMark()) {
                    max = tempExams.get(i);
                }
            }
            finalResult.add(max);
        });

        float tuSo = 0f;
        int mauSo = 0;
        for (int i = 0; i < finalResult.size(); i++) {
            ExamResult result = finalResult.get(i);
            tuSo += result.getMark() * result.getSubject().getCredit();
            mauSo += result.getSubject().getCredit();
        }

        if (mauSo == 0) {
            return 0f;
        }
        return tuSo / mauSo;
    }


    // tuning performance
    public float calculateStudentGpaVersion2(Long studentId) {
        String sql = "";
        float gpa = 0f;
        // goi sql de tinh
        return gpa;
    }
}
