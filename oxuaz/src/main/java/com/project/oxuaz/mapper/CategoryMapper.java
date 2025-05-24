package com.project.oxuaz.mapper;

import com.project.oxuaz.dto.response.CategoryResponse;
import com.project.oxuaz.entity.CategoryEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {
    public static CategoryResponse convertToDTO(CategoryEntity category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());

        return response;
    }

    public static List<CategoryResponse> convertToDTOList(List<CategoryEntity> groups) {
        return groups.stream()
                .map(CategoryMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
