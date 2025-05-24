package com.project.oxuaz.dto.request;

import lombok.Data;

@Data
public class UpdatePostRequest {

    private Long id;
    private String title;
    private String description;
    private Long categoryId;
}
