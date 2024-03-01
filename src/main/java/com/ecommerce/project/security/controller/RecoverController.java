package com.ecommerce.project.security.controller;

import com.ecommerce.project.security.services.recover.RecoverServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/recover")
public class RecoverController {

    @Autowired
    RecoverServiceImpl recoverService;

    @PostMapping("/forgotUsername")
    @ResponseBody
    public ResponseEntity<?> forgotUsername(@RequestParam(name = "emailTo") String emailTo)
    {
        return recoverService.forgotUsername(emailTo);
    }

    @PostMapping("/forgotPassword")
    @ResponseBody
    public ResponseEntity<?> forgotPassword(
            @RequestParam(name = "username") String username, @RequestParam(name = "emailTo") String emailTo)
    {
        return recoverService.forgotUsername(emailTo);
    }

}