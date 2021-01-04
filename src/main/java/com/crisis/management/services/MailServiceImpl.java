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
import com.twilio.Twilio;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
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
                    mimeMessageHelper.setText("<h2>Uwaga!</h2> Ostrzegamy przed możliwym zagrożeniem, szczegóły poniżej<br>" +
                            "Stacja: " + waterAlert.getWaterStation().getName() + "<br> Rodzaj zagrożenia: " + waterAlert.getWaterDanger().getDescription() + "<br>" +
                            "Dodatkowe szczegóły: " + waterAlert.getDescription() + "<br>Dodano: " + waterAlert.getPublishDate() + "<br> Pozdrawiamy, zespół ostrzegania przed kataklizmami", true);
                    javaMailSender.send(mimeMessage);
                }
                catch (MessagingException e) {
                    System.out.println("Błąd wysyłania maila");
                }
            });
        }
        else if(sendAlertEmailDto.getAlertType()==AlertType.WEATHER) {
            WeatherAlert weatherAlert = weatherAlertRepo.findById(sendAlertEmailDto.getAlertId()).orElseThrow(() -> new RuntimeException("Nie znaleziono alertu"));
            users.forEach(user -> {
                try {
                    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                    mimeMessageHelper.setTo(user.getEmail());
                    mimeMessageHelper.setSubject("Ostrzeżenie meteorologiczne");
                    mimeMessageHelper.setText("<h2>Uwaga!</h2> Ostrzegamy przed możliwym zagrożeniem, szczegóły poniżej<br> " +
                            "Stacja: " + weatherAlert.getWeatherStation().getName() + "<br> Rodzaj zagrożenia: " +weatherAlert.getWeatherDanger().getDescription() + "<br>" +
                            "Dodatkowe szczegóły: " + weatherAlert.getDescription() + "<br>Dodano " + weatherAlert.getPublishDate() + "<br> Pozdrawiamy, zespół ostrzegania przed kataklizmami", true);
                    javaMailSender.send(mimeMessage);
                }
                catch (MessagingException e) {
                    System.out.println("Błąd wysyłania maila");
                }
            });
        }
        else if(sendAlertEmailDto.getAlertType()==AlertType.OTHER) {
            Alert alert = alertRepo.findById(sendAlertEmailDto.getAlertId()).orElseThrow(() -> new RuntimeException("Nie znaleziono alertu"));
            users.forEach(user -> {
                try {
                    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                    mimeMessageHelper.setTo(user.getEmail());
                    mimeMessageHelper.setSubject("Ostrzeżenie przed zagrożeniem");
                    mimeMessageHelper.setText("<h2>Uwaga!</h2> Ostrzegamy przed możliwym zagrożeniem, szczegóły poniżej<br> " +
                            "Rodzaj zagrożenia: " + alert.getDngId().getDescription() + "<br>" + "Dodatkowe szczegóły: " + alert.getDescription() +
                            "<br>Dokładne współrzędne: " + alert.getLat() + "," + alert.getLng() + "<br>Dodano: " + alert.getPublishDate() + "<br> Pozdrawiamy, zespół ostrzegania przed kataklizmami", true);
                    javaMailSender.send(mimeMessage);
                }
                catch (MessagingException e) {
                    System.out.println("Błąd wysyłania maila");
                }
            });
        }
        return ResponseEntity.ok("Wysłano");
    }

    @Override
    public ResponseEntity<String> sendAlertBySms(SendAlertEmailDto sendAlertEmailDto) {
        List<User> users = userRepo.findAll();
        if(sendAlertEmailDto.getAlertType()== AlertType.WATER) {
            WaterAlert waterAlert = waterAlertRepo.findById(sendAlertEmailDto.getAlertId()).orElseThrow(() -> new RuntimeException("Nie znaleziono alertu"));
            users.forEach(user -> {
                if(user.getId()==1) {
                    String messText = "Uwaga, zagrozenie wodne w stacji: " + waterAlert.getWaterStation().getName() + ". Po szczegóły udaj sie na strone";
/*                    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                    Message message = Message.creator(new PhoneNumber("+48" + user.getTel()),
                            new PhoneNumber("+18315087366"),
                            messText).create();

                    System.out.println(message.getSid());*/
                    System.out.println("Wysyłam SMS wodny na numer" + "+48" + user.getTel() + " o tresci: " + messText);
                }
            });
        }
        else if(sendAlertEmailDto.getAlertType()==AlertType.WEATHER) {
            WeatherAlert weatherAlert = weatherAlertRepo.findById(sendAlertEmailDto.getAlertId()).orElseThrow(() -> new RuntimeException("Nie znaleziono alertu"));
            users.forEach(user -> {
                if(user.getId()==1) {
                    System.out.println("Wysyłam SMS wodny na numer" + user.getTel() + " o tresci: " + "Uwaga, zagrozenie meteo w stacji: " + weatherAlert.getWeatherStation().getName() + ". Po szczegóły udaj sie na strone");
                }
            });
        }
        else if(sendAlertEmailDto.getAlertType()==AlertType.OTHER) {
            Alert alert = alertRepo.findById(sendAlertEmailDto.getAlertId()).orElseThrow(() -> new RuntimeException("Nie znaleziono alertu"));
            users.forEach(user -> {
                if(user.getId()==1) {
                    System.out.println("Wysyłam SMS wodny na numer" + user.getTel() + " o tresci: " + "Uwaga, zagrozenie:  " + alert.getDngId().getDescription() + ". Po szczegóły udaj sie na strone");
                }
            });
        }
        return null;
    }
}
