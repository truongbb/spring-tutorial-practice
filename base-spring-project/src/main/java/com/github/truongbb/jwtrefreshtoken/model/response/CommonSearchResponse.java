package com.github.truongbb.jwtrefreshtoken.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonSearchResponse<T> {

    Integer totalPage;

    Long totalRecord;

    List<T> data;

    CommonPagingResponse pageInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CommonPagingResponse {
        int pageSize;
        int pageNumber;
    }

}
