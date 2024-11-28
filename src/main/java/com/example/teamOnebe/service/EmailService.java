package com.example.teamOnebe.service;

import com.example.teamOnebe.dto.EmailVerificationDto;
import com.example.teamOnebe.entity.EmailVerification;
import com.example.teamOnebe.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailVerificationRepository emailVerificationRepository;

    @Value("${spring.mail.username}")
    private String from;

    public void sendEmail(String to)
    {
        Long code =(long)(Math.random() * 8999) + 1000;
        if(!save(to,code)) return;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("{서비스명} 이메일 인증");
        message.setText("저희 서비스 가입을 환영합니다. \n인증번호: "+code+" 를 입력해주세요.");
        message.setFrom(from);
        mailSender.send(message);
    }

    public boolean verify(EmailVerificationDto emailVerificationDto)
    {
        return emailVerificationRepository.existsByEmailAndCode(emailVerificationDto.getEmail(),emailVerificationDto.getCode());
    }

    private boolean save(String email, Long code)
    {
        Optional<EmailVerification> _email = emailVerificationRepository.findByEmail(email);
        if(_email.isPresent())
        {
            EmailVerification emailVerification = _email.get();
            if(Duration.between(emailVerification.getCreatedDate(), LocalDateTime.now()).toMinutes() >= 3) //3분이 지났으면
            {
                emailVerificationRepository.delete(emailVerification);
            }
            else
            {
                return false;
            }
        }

        EmailVerification emailVerification = EmailVerification.builder()
                .email(email)
                .code(code)
                .build();
        emailVerificationRepository.save(emailVerification);
        return true;
    }

}
