package org.example.studentexammanagement.repository;

import org.example.studentexammanagement.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    List<ExamResult> findByStudentIdAndSubjectId(Long studentId, Long subjectId);


    List<ExamResult> findByStudentId(Long studentId);

    @Query(value = "with raw_data as (\n" +
            "    select student_id, subject_id, max(mark) mark\n" +
            "    from exam_results\n" +
            "    where student_id = :studentId\n" +
            "    group by student_id, subject_id\n" +
            "), full_data as (\n" +
            "    select r.*, s.credit\n" +
            "    from raw_data r\n" +
            "    left join subjects s on r.subject_id = s.id\n" +
            "), sum_data as (\n" +
            "    select sum(mark * credit) tu_so, sum(credit) mau_so\n" +
            "    from full_data\n" +
            ")\n" +
            "select\n" +
            "    case mau_so\n" +
            "        when 0 then 0\n" +
            "        else tu_so / mau_so\n" +
            "    end\n" +
            "from sum_data", nativeQuery = true)
    Float calculateStudentGpa(Long studentId);


}
