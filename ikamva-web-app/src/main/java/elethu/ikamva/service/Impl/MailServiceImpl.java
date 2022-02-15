package elethu.ikamva.service.Impl;

import elethu.ikamva.domain.Mail;
import elethu.ikamva.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Slf4j
@Service("mailService")
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(Mail mail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getFrom(), "zuko.yawa@gmail.com"));
            mimeMessageHelper.setTo(mail.getTo());
            mimeMessageHelper.setText(mail.getMailContent());

            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException ex) {
            LOGGER.info("There was an error sending an mail to email address: {}, because", mail.getTo());
            throw new MessagingException(String.format("Mail service Error: %s", ex.getMessage()));
        }catch (UnsupportedEncodingException e){
            LOGGER.info("Mail Service UnsupportedEncodingException: {}, because", mail.getTo());
        }
    }
}
