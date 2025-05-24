package com.project.oxuaz.service;

import com.project.oxuaz.dto.request.AddCategoryRequest;
import com.project.oxuaz.dto.request.UpdateCategoryRequest;
import com.project.oxuaz.dto.response.CategoryResponse;
import com.project.oxuaz.entity.CategoryEntity;
import com.project.oxuaz.exception.CustomException;
import com.project.oxuaz.mapper.CategoryMapper;
import com.project.oxuaz.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse add(AddCategoryRequest request) {
        CategoryEntity category = new CategoryEntity();
        category.setName(request.getName());

        categoryRepository.save(category);
        return CategoryMapper.convertToDTO(category);
    }

    public List<CategoryResponse> getAll() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return CategoryMapper.convertToDTOList(categories);
    }

    public CategoryResponse findById(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException("Kateqoriya tapılmadı", "Category not found", "Not found",
                        404, null));
        return CategoryMapper.convertToDTO(category);
    }

    public CategoryResponse update(UpdateCategoryRequest request) {
        CategoryEntity category = categoryRepository.findById(request.getId())
                .orElseThrow(() -> new CustomException("Kateqoriya tapılmadı", "Category not found", "Not found",
                        404, null));
        category.setName(request.getName());
        categoryRepository.save(category);

        return CategoryMapper.convertToDTO(category);
    }

    public void delete(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException("Kateqoriya tapılmadı", "Category not found", "Not found",
                        404, null));
        categoryRepository.delete(category);
    }
}
