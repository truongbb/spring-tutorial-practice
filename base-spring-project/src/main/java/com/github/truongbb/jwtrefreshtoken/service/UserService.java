package com.github.truongbb.jwtrefreshtoken.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.truongbb.jwtrefreshtoken.dto.SearchUserDto;
import com.github.truongbb.jwtrefreshtoken.entity.Role;
import com.github.truongbb.jwtrefreshtoken.entity.User;
import com.github.truongbb.jwtrefreshtoken.exception.ExistedUserException;
import com.github.truongbb.jwtrefreshtoken.exception.ObjectNotFoundException;
import com.github.truongbb.jwtrefreshtoken.model.request.CreateUserRequest;
import com.github.truongbb.jwtrefreshtoken.model.request.UserSearchRequest;
import com.github.truongbb.jwtrefreshtoken.model.response.CommonSearchResponse;
import com.github.truongbb.jwtrefreshtoken.model.response.UserResponse;
import com.github.truongbb.jwtrefreshtoken.model.response.UserSearchResponse;
import com.github.truongbb.jwtrefreshtoken.repository.RoleRepository;
import com.github.truongbb.jwtrefreshtoken.repository.UserRepository;
import com.github.truongbb.jwtrefreshtoken.repository.custom.UserCustomRepository;
import com.github.truongbb.jwtrefreshtoken.statics.Roles;
import com.github.truongbb.jwtrefreshtoken.statics.UserStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {

    final PasswordEncoder passwordEncoder;

    final UserRepository userRepository;

    final RoleRepository roleRepository;

    final ObjectMapper objectMapper;

    final UserCustomRepository userCustomRepository;

    public UserResponse getDetail(Long id) throws ObjectNotFoundException {
        return userRepository.findById(id)
                .map(u -> objectMapper.convertValue(u, UserResponse.class))
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }


    public UserResponse createUser(CreateUserRequest request) throws ExistedUserException {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            throw new ExistedUserException("Username existed");
        }

        Set<Role> roles = roleRepository.findByName(Roles.USER).stream().collect(Collectors.toSet());

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode("123")) // TODO: change to random password
                .roles(roles)
                .status(UserStatus.ACTIVATED)
                .build();
        userRepository.save(user);
        return objectMapper.convertValue(user, UserResponse.class);
    }

    public CommonSearchResponse<UserSearchResponse> searchUser(UserSearchRequest request) {
        List<SearchUserDto> result = userCustomRepository.searchUser(request);

        Long totalRecord = 0L;
        List<UserSearchResponse> studentResponses = new ArrayList<>();
        if (!result.isEmpty()) {
            totalRecord = result.get(0).getTotalRecord();
            studentResponses = result
                    .stream()
                    .map(s -> objectMapper.convertValue(s, UserSearchResponse.class))
                    .toList();
        }

        int totalPage = (int) Math.ceil((double) totalRecord / request.getPageSize());

        return CommonSearchResponse.<UserSearchResponse>builder()
                .totalRecord(totalRecord)
                .totalPage(totalPage)
                .data(studentResponses)
                .pageInfo(new CommonSearchResponse.CommonPagingResponse(request.getPageSize(), request.getPageIndex()))
                .build();
    }

}
