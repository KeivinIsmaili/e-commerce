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

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;

import static com.ecommerce.project.utils.Constants.AES_CIPHER_ALGORITHM;
import static com.ecommerce.project.utils.Constants.AES_SECRET_KEY;

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

        String decryptedtoken = decryptToken(token);

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(decryptedtoken);

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

    @Override
    public String decryptToken(String encryptedToken) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(AES_SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedToken));
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
