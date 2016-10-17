package spring.generic.server.Utills;

/**
 * Created by gadiel on 15/10/2016.
 */
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailClient {
    private MailSender mailSender;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String from, String to, String subject, String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
    }
}