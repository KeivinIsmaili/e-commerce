package com.ecommerce.project.security.services.securityinfoservice;

import com.ecommerce.project.security.model.User;
import org.springframework.http.ResponseEntity;

public interface SecurityInfoService {

    ResponseEntity<?> changePassword(User user);

}
