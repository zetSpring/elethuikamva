package elethu.ikamva.service;

import elethu.ikamva.domain.Mail;

import jakarta.mail.MessagingException;

public interface MailService {
    void sendEmail(Mail mail) throws MessagingException;
}
