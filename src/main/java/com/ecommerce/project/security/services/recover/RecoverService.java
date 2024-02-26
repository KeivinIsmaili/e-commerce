package com.ecommerce.project.security.services.recover;

import org.springframework.http.ResponseEntity;

public interface RecoverService {

    ResponseEntity<?> forgotUsername(String email);

    ResponseEntity<?> forgotPassword(String username, String email);

}
