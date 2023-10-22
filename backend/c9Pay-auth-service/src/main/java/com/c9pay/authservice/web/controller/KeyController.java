package com.c9pay.authservice.web.controller;

import com.c9pay.authservice.web.dto.PublicKeyResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeyController {
    private final String publicKey;

    public KeyController(@Value("${auth.public-key}") String stringPublicKey) {
        this.publicKey = stringPublicKey;
    }

    @GetMapping("/public-key")
    public ResponseEntity<?> getPublicKey() {
        return ResponseEntity.ok(new PublicKeyResponse(publicKey));
    }
}
