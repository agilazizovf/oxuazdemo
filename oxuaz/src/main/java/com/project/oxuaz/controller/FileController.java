package com.project.oxuaz.controller;

import com.project.oxuaz.dto.response.FileResponse;
import com.project.oxuaz.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@MyRestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService service;
    private final Path rootLocation = Paths.get("C:\\Users\\VICTUS\\Documents\\GitHub\\oxuazdemo\\oxuaz\\files");

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> addFile(@RequestParam(name = "file") MultipartFile file) throws IOException {
        return service.uploadFile(file);
    }

    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(file);
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }
}