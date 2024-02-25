package com.ecommerce.project.security.controller;

import com.ecommerce.project.security.payload.request.SignupRequest;
import com.ecommerce.project.security.payload.response.JwtResponse;
import com.ecommerce.project.security.services.authentication.AuthenticationServiceImpl;
import com.ecommerce.project.security.payload.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    AuthenticationServiceImpl authenticationService;

    @PostMapping("/signin")
    @ResponseBody
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest)
    {
        return authenticationService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest)
    {
        return authenticationService.registerUser(signUpRequest);
    }

    @PostMapping("/forgotUsername")
    @ResponseBody
    public ResponseEntity<?> forgotUsername()
    {
        return null;
    }

    @PostMapping("/forgotPassword")
    @ResponseBody
    public ResponseEntity<?> forgotPassword()
    {
        return null;
    }
}
