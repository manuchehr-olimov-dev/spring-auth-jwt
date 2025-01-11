package com.mansur_university.CheckStockBot.Https.Controllers;

import com.mansur_university.CheckStockBot.Database.Models.User;
import com.mansur_university.CheckStockBot.Https.Responses.BasicResponse;
import org.springframework.http.HttpStatus;
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
