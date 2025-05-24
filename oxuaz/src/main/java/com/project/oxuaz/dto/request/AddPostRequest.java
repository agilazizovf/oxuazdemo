package com.project.oxuaz.dto.request;

import lombok.Data;

@Data
public class AddPostRequest {

    private String title;
    private String description;
    private Long categoryId;
}
