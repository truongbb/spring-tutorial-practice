package com.github.truongbb.jwtrefreshtoken.repository.custom;

import com.github.truongbb.jwtrefreshtoken.model.request.UserSearchRequest;
import com.github.truongbb.jwtrefreshtoken.model.response.UserSearchResponse;
import com.github.truongbb.jwtrefreshtoken.repository.BaseRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserCustomRepository extends BaseRepository {

    public List<UserSearchResponse> searchUser(UserSearchRequest request) {
        String sql = "select u.username name, u.gender gender from users u where 1 = 1";

        Map<String, Object> parameters = new HashMap<>();

//        if(StringUtils.isEmpty(request.getEmail())) {
        if (request.getEmail() != null && !request.getEmail().trim().equals("")) {
            sql += " and lower(u.email) like :email";
            parameters.put("email", "%" + request.getEmail().toLowerCase() + "%");
        }

        if (request.getName() != null && !request.getName().trim().equals("")) {
            sql += " and lower(u.username) like :name";
            parameters.put("name", "%" + request.getName().toLowerCase() + "%");
        }

        if (request.getActivated() != null) {
            sql += " and u.activated = :activated";
            parameters.put("activated", request.getActivated());
        }

        if (request.getGender() != null && !request.getGender().trim().equals("")) {
            sql += " and u.gender = :gender";
            parameters.put("gender", request.getGender());
        }

        return getNamedParameterJdbcTemplate().query(sql, parameters, BeanPropertyRowMapper.newInstance(UserSearchResponse.class));
    }

}
