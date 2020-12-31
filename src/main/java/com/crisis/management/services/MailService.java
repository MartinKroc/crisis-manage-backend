package com.crisis.management.services;

import com.crisis.management.dto.SendAlertEmailDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

public interface MailService {
    void sendMail(String to, String subject, String text, boolean isHtmlContent) throws MessagingException;
    ResponseEntity<String> sendAlert(SendAlertEmailDto sendAlertEmailDto) throws MessagingException;
    ResponseEntity<String> sendAlertBySms(SendAlertEmailDto sendAlertEmailDto);
}
