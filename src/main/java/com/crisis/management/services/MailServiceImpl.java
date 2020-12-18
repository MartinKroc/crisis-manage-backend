package com.crisis.management.services;

import com.crisis.management.dto.SendAlertEmailDto;
import com.crisis.management.models.Alert;
import com.crisis.management.models.User;
import com.crisis.management.models.WaterAlert;
import com.crisis.management.models.WeatherAlert;
import com.crisis.management.models.enums.AlertType;
import com.crisis.management.repo.AlertRepo;
import com.crisis.management.repo.UserRepo;
import com.crisis.management.repo.WaterAlertRepo;
import com.crisis.management.repo.WeatherAlertRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    private JavaMailSender javaMailSender;
    private UserRepo userRepo;
    private WaterAlertRepo waterAlertRepo;
    private WeatherAlertRepo weatherAlertRepo;
    private AlertRepo alertRepo;

    @Override
    public void sendMail(String to,
                         String subject,
                         String text,
                         boolean isHtmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public ResponseEntity<String> sendAlert(SendAlertEmailDto sendAlertEmailDto) throws MessagingException {
        List<User> users = userRepo.findAll();
        if(sendAlertEmailDto.getAlertType()== AlertType.WATER) {
            WaterAlert waterAlert = waterAlertRepo.findById(sendAlertEmailDto.getAlertId()).orElseThrow(() -> new RuntimeException("Nie znaleziono alertu"));
            users.forEach(user -> {
                try {
                    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                    mimeMessageHelper.setTo(user.getEmail());
                    mimeMessageHelper.setSubject("Ostrzeżenie hydrologiczne");
                    mimeMessageHelper.setText("Ostrzeżnie przed zagrożeniem " + waterAlert.getDescription(), true);
                    javaMailSender.send(mimeMessage);
                }
                catch (MessagingException e) {
                    System.out.println("Błąd wysyłania maila");
                }
            });
        }
        else if(sendAlertEmailDto.getAlertType()==AlertType.WEATHER) {
            WeatherAlert weatherAlert = weatherAlertRepo.findById(sendAlertEmailDto.getAlertId()).orElseThrow(() -> new RuntimeException("Nie znaleziono alertu"));
        }
        else if(sendAlertEmailDto.getAlertType()==AlertType.OTHER) {
            Alert alert = alertRepo.findById(sendAlertEmailDto.getAlertId()).orElseThrow(() -> new RuntimeException("Nie znaleziono alertu"));
        }
        return ResponseEntity.ok("Wysłano");
    }
}
