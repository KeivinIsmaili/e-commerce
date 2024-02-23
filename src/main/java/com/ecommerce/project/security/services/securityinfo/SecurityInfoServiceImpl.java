package com.ecommerce.project.security.services.securityinfo;

import com.ecommerce.project.security.model.User;
import com.ecommerce.project.security.payload.response.MessageResponse;
import com.ecommerce.project.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class SecurityInfoServiceImpl implements SecurityInfoService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public ResponseEntity<?> changePassword(User updatedUser) {

        User user = Optional.ofNullable(userRepository.findByUsername(updatedUser.getUsername()))
                .orElseThrow(() -> new UsernameNotFoundException("User with this username doesn't exist"));

        user.setPassword(encoder.encode(updatedUser.getPassword()));
        return ResponseEntity.ok(new MessageResponse("User's password changed successfully!"));
    }

}
