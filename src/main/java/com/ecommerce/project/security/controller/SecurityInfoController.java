package com.ecommerce.project.security.controller;

import com.ecommerce.project.security.services.securityinfo.SecurityInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/securityInfo")
public class SecurityInfoController {

    @Autowired
    private SecurityInfoServiceImpl securityInfoService;

    @PutMapping("/changePassword")
    @ResponseBody
    public ResponseEntity<?> changePassword(@RequestParam(name = "newPassword") String newPassword,
                                            @RequestParam(name = "token") String token)
    {
        return securityInfoService.changePassword(newPassword, token);
    }

}