package org.example.studentexammanagement.repository.custom;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExamResultCustomRepository {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Double calculateStudentGpa(Long studentId) {
        // named parameter query
        String query = "with raw_data as (\n" +
                "    select student_id, subject_id, max(mark) mark\n" +
                "    from exam_results\n" +
                "    where student_id = :p_student_id\n" +
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
                "from sum_data";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("p_student_id", studentId);
        return namedParameterJdbcTemplate.queryForObject(query, parameters, new SingleColumnRowMapper<>(Double.class));
    }

}
