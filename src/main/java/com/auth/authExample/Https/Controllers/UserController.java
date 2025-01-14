package com.auth.authExample.Https.Controllers;

import com.auth.authExample.Https.Responses.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/a")
    public ResponseEntity<BasicResponse> hello() {

        return ResponseEntity.ok(new BasicResponse("Hello World"));
    }

}
