package org.example.studentexammanagement.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonSearchResponse<T> {
    Long totalRecord;

    int totalPage;

    List<T> data;

    CommonSearchPageInfo paging;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CommonSearchPageInfo {
        int pageSize;
        int pageIndex;

    }

}
