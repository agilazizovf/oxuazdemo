package com.project.oxuaz.service;

import com.project.oxuaz.dto.request.AddPostRequest;
import com.project.oxuaz.dto.request.UpdatePostRequest;
import com.project.oxuaz.dto.response.FileResponse;
import com.project.oxuaz.dto.response.PostResponse;
import com.project.oxuaz.entity.CategoryEntity;
import com.project.oxuaz.entity.PostEntity;
import com.project.oxuaz.exception.CustomException;
import com.project.oxuaz.mapper.PostMapper;
import com.project.oxuaz.repository.CategoryRepository;
import com.project.oxuaz.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    public PostResponse add(AddPostRequest request) {
        CategoryEntity category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CustomException("Kateqoriya tapılmadı", "Category not found", "Not found",
                        404, null));
        PostEntity post = new PostEntity();
        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setCategory(category);

        postRepository.save(post);

        return PostMapper.convertToDTO(post);
    }

    public List<PostResponse> getAll() {
        List<PostEntity> posts = postRepository.findAll();
        return PostMapper.convertToDTOList(posts);
    }

    public PostResponse findById(Long id) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException("Post tapılmadı", "Post not found", "Not found",
                        404, null));
        return PostMapper.convertToDTO(post);
    }

    public PostResponse update(UpdatePostRequest request) {
        PostEntity post = postRepository.findById(request.getId())
                .orElseThrow(() -> new CustomException("Post tapılmadı", "Post not found", "Not found",
                        404, null));

        CategoryEntity category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CustomException("Kateqoriya tapılmadı", "Category not found", "Not found",
                        404, null));

        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setUpdatedAt(LocalDateTime.now());
        post.setCategory(category);

        postRepository.save(post);

        return PostMapper.convertToDTO(post);
    }

    public void delete(Long id) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException("Post tapılmadı", "Post not found", "Not found",
                        404, null));
        postRepository.delete(post);
    }

    public List<PostResponse> findPostsByCategoryId(Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException("Kateqoriya tapılmadı", "Category not found", "Not found",
                        404, null));

        List<PostEntity> posts = postRepository.findPostsByCategoryId(category.getId());

        return PostMapper.convertToDTOList(posts);
    }

    public PostResponse uploadFileToPost(Long postId, MultipartFile file) throws IOException {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException("Post tapılmadı", "Post not found", "Not found",
                        404, null));

        // Delete old file if exists
        String oldFileName = post.getUrl();
        if (oldFileName != null && !oldFileName.isEmpty()) {
            fileService.deleteFile(oldFileName);
        }

        // Upload new file
        ResponseEntity<FileResponse> uploadedFileResponse = fileService.uploadFile(file);
        String newFileName = uploadedFileResponse.getBody().getUuidName();

        post.setUrl(newFileName);
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        return PostMapper.convertToDTO(post);
    }

    public void deleteFileFromPost(Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException("Post tapılmadı", "Post not found", "Not found",
                        404, null));

        String fileName = post.getUrl();
        if (fileName != null && !fileName.isEmpty()) {
            fileService.deleteFile(fileName);
            post.setUrl(null);
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);
        }
    }
}
