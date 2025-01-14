package com.auth.authExample.Https.Requests.Auth;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String name;
    private String lastName;
    private String email;
    private String password;
}
