package com.ecommerce.project.security.services.recover;

import com.ecommerce.project.security.model.User;
import org.springframework.http.ResponseEntity;

public interface RecoverService {

    ResponseEntity<?> forgotUsername(String email);

    ResponseEntity<?> forgotPassword(String username, String email);

    String createPasswordResetTokenForUser(User user);

    String encryptToken(String token);

}
