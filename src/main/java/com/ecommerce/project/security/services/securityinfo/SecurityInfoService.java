package com.ecommerce.project.security.services.securityinfo;

import org.springframework.http.ResponseEntity;

public interface SecurityInfoService {

    ResponseEntity<?> changePassword(String newPassword, String token);

    String decryptToken(String encryptedToken);

}
