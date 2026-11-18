package calamadevs.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;
    public void sendPasswordResetEmail(String toEmail, String token) {

        try {

            SimpleMailMessage message = getSimpleMailMessage(toEmail, token);
            mailSender.send(message);
            log.info("Recovery email successfully sento to: {}", toEmail);

        } catch (Exception e) {

            log.error("Error sending email to {}: {}", toEmail, e.getMessage());
            throw new RuntimeException("Failed to send recovery email. Check your credentials or try again.");
        }

    }
    private @NonNull SimpleMailMessage getSimpleMailMessage(String toEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("SmartRoad - Password recovery");
        message.setText("Hello!\n\n"
                + "You have requested a password recovery for SmartRoad.\n\n"
                + "Your recovery token is:\n"
                + token + "\n\n"
                + "This token is valid for 15 minutes.\n"
                + "If you did not request a password change, you can safely ignore this email.");
        return message;
    }
}


