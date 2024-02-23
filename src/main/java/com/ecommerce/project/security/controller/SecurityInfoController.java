package com.ecommerce.project.security.controller;

import com.ecommerce.project.security.model.User;
import com.ecommerce.project.security.services.securityinfo.SecurityInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/securityInfo")
public class SecurityInfoController {

    @Autowired
    private SecurityInfoServiceImpl securityInfoService;

    @PutMapping("/changePassword")
    @ResponseBody
    public ResponseEntity<?> changePassword(@RequestBody User user)
    {
        return securityInfoService.changePassword(user);
    }

}