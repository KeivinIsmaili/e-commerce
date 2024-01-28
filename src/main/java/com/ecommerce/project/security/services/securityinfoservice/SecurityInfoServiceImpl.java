package com.ecommerce.project.security.services.securityinfoservice;

import com.ecommerce.project.security.model.User;
import com.ecommerce.project.security.payload.response.MessageResponse;
import com.ecommerce.project.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityInfoServiceImpl implements SecurityInfoService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    @Transactional
    public ResponseEntity<?> changePassword(User updatedUser) {
        if(updatedUser.getUsername() != null) {
            User user1 = userRepository.findByUsername(updatedUser.getUsername());
            if (user1 != null) {
                user1.setPassword(encoder.encode(updatedUser.getPassword()));
                return ResponseEntity.ok(new MessageResponse("User's password changed successfully!"));
            } else {
                throw new UsernameNotFoundException("User with this username not found");
            }
        } else {
            throw new IllegalArgumentException("User with this id not found");
        }
    }

}
