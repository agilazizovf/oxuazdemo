package com.project.oxuaz.controller;

import com.project.oxuaz.dto.request.AddPostRequest;
import com.project.oxuaz.dto.request.UpdatePostRequest;
import com.project.oxuaz.dto.response.PostResponse;
import com.project.oxuaz.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@MyRestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<PostResponse> addPost(@RequestBody AddPostRequest request) {
        PostResponse response = postService.add(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse post = postService.findById(id);
        return ResponseEntity.ok(post);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<PostResponse> updatePost(@RequestBody UpdatePostRequest request) {
        PostResponse updated = postService.update(request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostResponse>> getPostsByCategory(@PathVariable Long categoryId) {
        List<PostResponse> posts = postService.findPostsByCategoryId(categoryId);
        return ResponseEntity.ok(posts);
    }

    @PostMapping(value = "/{postId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> uploadFileToPost(@PathVariable Long postId,
                                                         @RequestParam("file") MultipartFile file) throws IOException {
        PostResponse updated = postService.uploadFileToPost(postId, file);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{postId}/file")
    public ResponseEntity<Void> deletePostFile(@PathVariable Long postId) {
        postService.deleteFileFromPost(postId);
        return ResponseEntity.noContent().build();
    }
}
