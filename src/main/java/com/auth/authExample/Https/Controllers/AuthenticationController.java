package com.auth.authExample.Https.Controllers;

import com.auth.authExample.Https.Requests.Auth.AuthenticationRequest;
import com.auth.authExample.Https.Requests.Auth.RegisterRequest;
import com.auth.authExample.Https.Responses.Auth.AuthenticationResponse;
import com.auth.authExample.Https.Services.Auth.AuthenticationService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody RegisterRequest request
    ) {
        System.out.println(request);
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @GetMapping("/a")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        System.out.println("=================================");
        System.out.println( request.getEmail());
        System.out.println("=================================");

        return null;
//        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
