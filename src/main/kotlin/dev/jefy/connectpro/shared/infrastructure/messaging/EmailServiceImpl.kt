package dev.jefy.connectpro.shared.infrastructure.messaging;


import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.EmailStrategy;
import dev.jefy.connectpro.user.domain.vo.Email;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sendingEmail;


    @Async
    @Override
    public void sendEmail(Email email, EmailStrategy strategy) {
        String message = strategy.getMessage();
        String subject = strategy.getSubject();
        try {
            doSendEmail(email.value(), message, subject);
        } catch (MessagingException e) {
            throw new EmailNotSentException();
        }
    }

    private void doSendEmail(String toEmail, String message, String subject) throws MessagingException {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sendingEmail);
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
