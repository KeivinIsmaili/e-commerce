package com.ecommerce.project.security.services.securityinfo;

import com.ecommerce.project.security.model.PasswordResetToken;
import com.ecommerce.project.security.model.User;
import com.ecommerce.project.security.payload.response.MessageResponse;
import com.ecommerce.project.security.repository.PasswordResetTokenRepository;
import com.ecommerce.project.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class SecurityInfoServiceImpl implements SecurityInfoService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public ResponseEntity<?> changePassword(String newPassword,String token)
    {
        LocalDateTime now = LocalDateTime.now();

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken == null || now.compareTo(passwordResetToken.getIssued_at()) < 0)
        {
            return ResponseEntity.badRequest().body(new MessageResponse("Token provided is invalid!"));
        }

        if(now.compareTo(passwordResetToken.getExpires_at()) > 0)
        {
            return ResponseEntity.badRequest().body(new MessageResponse("Token provided is expired!"));
        }

        User user = passwordResetToken.getUser();
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse(user.getUsername() + "'s password changed successfully!"));
    }

}
