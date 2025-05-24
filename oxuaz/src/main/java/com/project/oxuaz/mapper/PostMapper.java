package com.project.oxuaz.mapper;

import com.project.oxuaz.dto.response.PostResponse;
import com.project.oxuaz.entity.PostEntity;

import java.util.List;
import java.util.stream.Collectors;

public class PostMapper {

    public static PostResponse convertToDTO(PostEntity post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setDescription(post.getDescription());
        response.setUrl(post.getUrl());
        response.setCreatedAt(post.getCreatedAt());
        response.setUpdatedAt(post.getUpdatedAt());

        // Assuming post.getCategory() returns a CategoryEntity or categoryId directly
        if (post.getCategory() != null) {
            response.setCategoryId(post.getCategory().getId());
        } else {
            response.setCategoryId(null);
        }

        return response;
    }

    public static List<PostResponse> convertToDTOList(List<PostEntity> posts) {
        return posts.stream()
                .map(PostMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
