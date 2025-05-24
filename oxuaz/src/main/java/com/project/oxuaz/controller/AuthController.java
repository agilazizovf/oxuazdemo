package com.project.oxuaz.controller;

import com.project.oxuaz.dto.request.AuthRequest;
import com.project.oxuaz.exception.CustomException;
import com.project.oxuaz.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@MyRestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest loginReq, BindingResult br) throws Exception {
        if (br.hasErrors()) {
            throw new CustomException("Məlumatların tamlığı pozulub!", "Validation failed!", "Validation", 400, br);
        }
        return authService.login(loginReq);
    }
}
