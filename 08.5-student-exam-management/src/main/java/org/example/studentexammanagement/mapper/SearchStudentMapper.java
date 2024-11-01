package org.example.studentexammanagement.mapper;

import org.example.studentexammanagement.dto.SearchStudentDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchStudentMapper extends BeanPropertyRowMapper<SearchStudentDto> {

    @Override
    public SearchStudentDto mapRow(ResultSet rs, int rowNumber) throws SQLException {
        return SearchStudentDto.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("ho_va_ten"))
                .email(rs.getString("email"))
                .phone(rs.getString("so_dien_thoai"))
                .gpa(rs.getFloat("diem_trung_binh"))
                .totalRecord(rs.getLong("totalRecord"))
                .gender(rs.getString("gender"))
                .dob(rs.getDate("dob").toLocalDate())
                .build();
    }
}
