package com.ecommerce.project.security.services.recover;

import com.ecommerce.project.security.model.User;
import com.ecommerce.project.security.payload.response.MessageResponse;
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

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RecoverServiceImpl implements RecoverService {

    private static final String USERNAME_RECOVER = "Your Requested Username Information";

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public ResponseEntity<?> forgotUsername(String emailTo) {

        boolean emailExists = userRepository.existsByEmail(emailTo);

        List<User> userList = userRepository.findAll();

        Optional<String> foundUsername = userList.stream()
                .filter(user -> emailTo.equals(user.getEmail()))
                .map(User::getUsername)
                .findFirst();
        
        if (emailExists && foundUsername.isPresent()) {
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
                return ResponseEntity
                        .badRequest()
                        .body(e.getMessage());
            }
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("No username is found with the provided email!"));
        }

    }

    @Override
    public ResponseEntity<?> forgotPassword(String username, String emailTo) {
        return null;
    }

}