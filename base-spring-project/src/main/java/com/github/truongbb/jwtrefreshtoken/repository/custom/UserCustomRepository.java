package com.github.truongbb.jwtrefreshtoken.repository.custom;

import com.github.truongbb.jwtrefreshtoken.dto.SearchUserDto;
import com.github.truongbb.jwtrefreshtoken.model.request.UserSearchRequest;
import com.github.truongbb.jwtrefreshtoken.repository.BaseRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserCustomRepository extends BaseRepository {

    public List<SearchUserDto> searchUser(UserSearchRequest request) {
        String query = "with raw_data as (\n" +
                "    select id, username, status\n" +
                "    from users\n" +
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
//        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
//            query += " and lower(u.email) like :email";
//            parameters.put("email", "%" + request.getEmail().toLowerCase() + "%");
//        }

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            query += " and lower(u.username) like :name";
            parameters.put("name", "%" + request.getName().toLowerCase() + "%");
        }

//        if (request.getActivated() != null) {
//            query += " and u.activated = :activated";
//            parameters.put("activated", request.getActivated());
//        }

//        if (request.getGender() != null && !request.getGender().trim().isEmpty()) {
//            query += " and u.gender = :gender";
//            parameters.put("gender", request.getGender());
//        }
        query = query.replace("{{search_condition}}", searchCondition);
        parameters.put("p_page_size", request.getPageSize());
        parameters.put("p_offset", request.getPageSize() * request.getPageIndex());

        return getNamedParameterJdbcTemplate().query(query, parameters, new BeanPropertyRowMapper<>(SearchUserDto.class));
    }

}
