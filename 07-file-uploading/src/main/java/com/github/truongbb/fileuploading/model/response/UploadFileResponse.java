package com.github.truongbb.fileuploading.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadFileResponse {

    List<String> fileUrls;

}
