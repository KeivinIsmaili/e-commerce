package com.ecommerce.project.security.services.recover;

import com.ecommerce.project.security.model.PasswordResetToken;
import com.ecommerce.project.security.model.User;
import com.ecommerce.project.security.payload.response.MessageResponse;
import com.ecommerce.project.security.repository.PasswordResetTokenRepository;
import com.ecommerce.project.security.repository.UserRepository;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RecoverServiceImpl implements RecoverService {

    private static final String USERNAME_RECOVER = "Your Requested Username Information";
    private static final String AES_SECRET_KEY = "y5@,D9UF=iNjq'QD$u[}k/{2-I(ny@u'";
    private static final String AES_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public ResponseEntity<?> forgotUsername(String emailTo) {

        boolean emailExists = userRepository.existsByEmail(emailTo);

        if (!emailExists) {
            return ResponseEntity.badRequest().body(new MessageResponse("No email is found!"));
        }

        List<User> userList = userRepository.findAll();

        Optional<String> foundUsername = userList.stream()
                .filter(user -> emailTo.equals(user.getEmail()))
                .map(User::getUsername)
                .findFirst();

        if (!foundUsername.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("No username is found with the provided email!"));
        }

        try {

            MimeMessage message = emailSender.createMimeMessage();
            message.setFrom(fromEmail);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject(USERNAME_RECOVER);
            String emailContent = "Hello,<br><br>"
                    + "You recently requested your username. Here it is: <b>" + foundUsername.get() + "</b><br><br>"
                    + "If you did not request this information, please ignore this email or contact us if you believe this is an error.<br><br>"
                    + "Thank you,<br>"
                    + "The Support Team";

            message.setContent(emailContent, "text/html; charset=utf-8");

            emailSender.send(message);

            return ResponseEntity.ok(new MessageResponse("Email sent successfully!"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> forgotPassword(String username, String emailTo)
    {

        boolean usernameExists = userRepository.existsByUsername(username);

        if (!usernameExists) {
            return ResponseEntity.badRequest().body(new MessageResponse("No username found!"));
        }

        boolean emailExists = userRepository.existsByEmail(emailTo);

        if (!emailExists) {
            return ResponseEntity.badRequest().body(new MessageResponse("No email is found with the proviced username!"));
        }

        User user = userRepository.findByUsername(username);

        String token = createPasswordResetTokenForUser(user);

        String resetLink = "http://localhost:4200/reset-password?token=" + token;

        try {

            MimeMessage message = emailSender.createMimeMessage();
            message.setFrom(fromEmail);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject(USERNAME_RECOVER);
            String emailContent = "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<meta charset='UTF-8'>"
                    + "<title>Password Reset Request</title>"
                    + "</head>"
                    + "<body>"
                    + "<p>Hello,</p>"
                    + "<p>You have requested to reset your password. Please click on the link below to set a new password:</p>"
                    + "<a href='" + resetLink + "'>Reset Password</a>"
                    + "<p>This link will expire in 60 minutes.</p>"
                    + "<p>If you did not request a password reset, please ignore this email or contact support if you have concerns.</p>"
                    + "<p>Thank you,</p>"
                    + "<p>Your Application Team</p>"
                    + "</body>"
                    + "</html>";

            message.setContent(emailContent, "text/html; charset=utf-8");

            emailSender.send(message);

            return ResponseEntity.ok(new MessageResponse("Email sent successfully!"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @Override
    public String createPasswordResetTokenForUser(User user) {
        String encryptedToken = encryptToken(UUID.randomUUID().toString());
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, encryptedToken);
        passwordResetToken.setIssued_at(LocalDateTime.now());
        passwordResetToken.setExpires_at(passwordResetToken.getIssued_at().plusHours(1));
        tokenRepository.save(passwordResetToken);
        return encryptedToken;
    }

    @Override
    public String encryptToken(String token) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(AES_SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(token.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}