package com.example.Medical.Controller;

import com.example.Medical.DTO.ChatRequest;
import com.example.Medical.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<String> chat(@RequestBody ChatRequest request,
                                       Principal principal) {

        String doctorEmail = principal.getName();

        String response = chatService.handleQuery(
                request.getMessage(),
                doctorEmail
        );

        return ResponseEntity.ok(response);
    }
}