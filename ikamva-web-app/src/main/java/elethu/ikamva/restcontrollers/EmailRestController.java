package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.Mail;
import elethu.ikamva.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailRestController {
    private final MailService mailService;

    @PostMapping("/send")
    ResponseEntity<String> sendMail(@RequestBody Mail mail) throws MessagingException {
        mailService.sendEmail(mail);

        return new ResponseEntity<>(String.format("Successfully sent mail to: %s", mail.getTo()), HttpStatus.ACCEPTED);
    }
}
