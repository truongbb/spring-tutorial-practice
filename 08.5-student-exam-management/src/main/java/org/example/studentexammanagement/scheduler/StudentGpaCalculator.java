package org.example.studentexammanagement.scheduler;

import lombok.AllArgsConstructor;
import org.example.studentexammanagement.entity.Student;
import org.example.studentexammanagement.repository.ExamResultRepository;
import org.example.studentexammanagement.repository.StudentRepository;
import org.example.studentexammanagement.service.ExamResultService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class StudentGpaCalculator {

    StudentRepository studentRepository;
    ExamResultService examResultService;
    ExamResultRepository examResultRepository;

    /**
     * realtime
     * <p>
     * CÁCH 2: tính điểm trung bình một cách định kỳ
     * Sau 1 khoảng thời gian co định
     * - cứ 2h tính 1 lâần, nửa nga tính 1 lâần, ...
     * - cứ 0h của ngay moi thì tính 1 ần
     * <p>
     * ==> Cron job (scheduler)
     */

//    @Scheduled(cron = "0 30 5 * * *")
    @Scheduled(fixedRateString = "7200000", fixedDelayString = "600000") // miliseconds
    public void calculateStudentGpa() {
        // lay ra danh sach sinh vien
        List<Student> students = studentRepository.findAll();
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
//            float gpa = examResultService.calculateStudentGpa(student.getId());
            float gpa = examResultRepository.calculateStudentGpa(student.getId());
            student.setGpa(gpa);
        }
        // batch
        studentRepository.saveAll(students); // tốn connection
    }

}
