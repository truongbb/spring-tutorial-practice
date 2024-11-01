package org.example.studentexammanagement.repository.custom;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.studentexammanagement.dto.SearchStudentDto;
import org.example.studentexammanagement.mapper.SearchStudentMapper;
import org.example.studentexammanagement.model.request.SearchStudentRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentCustomRepository {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<SearchStudentDto> searchStudent(SearchStudentRequest request) {
        String query = "with raw_data as (\n" +
                "    select id, name ho_va_ten, email, phone so_dien_thoai, gpa diem_trung_binh, gender, dob\n" +
                "    from students\n" +
                "    where 1 = 1\n" +
                "   {{search_condition}}\n" +
                "), count_data as(\n" +
                "    select count(*) totalRecord\n" +
                "    from raw_data\n" +
                ")\n" +
                "select r.*, c.totalRecord\n" +
                "from raw_data r, count_data c\n" +
                "limit :p_page_size\n" +
                "offset :p_offset";

        Map<String, Object> parameters = new HashMap<>();
        String searchCondition = "";
        if (request.getName() != null && !request.getName().isBlank()) {
            parameters.put("p_name", "%" + request.getName().toLowerCase() + "%");
            searchCondition += "and lower(name) like :p_name\n";
        }
        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            parameters.put("p_phone", "%" + request.getPhone().toLowerCase() + "%");
            searchCondition += "and lower(phone) like :p_phone\n";
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            parameters.put("p_email", "%" + request.getEmail().toLowerCase() + "%");
            searchCondition += "and lower(email) like :p_email\n";
        }
        if (request.getGpaStart() != null) {
            parameters.put("p_gpa_start", request.getGpaStart());
            searchCondition += "and gpa >= :p_gpa_start\n";
        }
        if (request.getGpaEnd() != null) {
            parameters.put("p_gpa_end", request.getGpaEnd());
            searchCondition += "and gpa <= :p_gpa_end\n";
        }
        query = query.replace("{{search_condition}}", searchCondition);
        parameters.put("p_page_size", request.getPageSize());
        parameters.put("p_offset", request.getPageSize() * request.getPageIndex());

//        System.out.println(query);
//        System.out.println(parameters);


//        return namedParameterJdbcTemplate.queryForList(query, parameters, SearchStudentDto.class);

//        return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(SearchStudentDto.class));

        return namedParameterJdbcTemplate.query(query, parameters, new SearchStudentMapper());

//        return namedParameterJdbcTemplate.query(query, parameters, new RowMapper<SearchStudentDto>() {
//            @Override
//            public SearchStudentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return SearchStudentDto.builder()
//                        .id(rs.getLong("id"))
//                        .name(rs.getString("ho_va_ten"))
//                        .email(rs.getString("email"))
//                        .phone(rs.getString("so_dien_thoai"))
//                        .gpa(rs.getFloat("diem_trung_binh"))
//                        .totalRecord(rs.getLong("totalRecord"))
//                        .build();
//            }
//        });
    }

}
