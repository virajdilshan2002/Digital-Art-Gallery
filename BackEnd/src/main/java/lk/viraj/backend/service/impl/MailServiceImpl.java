package lk.viraj.backend.service.impl;

import jakarta.mail.internet.MimeMessage;
import lk.viraj.backend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendAnEmail(String toEmail, String subject) {
        String htmlBody = """
                <html>
                  <body style="font-family: sans-serif;">
                    <h2>Welcome to Digital Art Gallery!</h2>
                    <p>Thanks for signing up. We're excited to have you.</p>
                    <hr>
                    <p style="font-size: 12px; color: gray;">This is an automated email. Please don't reply.</p>
                  </body>
                </html>
                """;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("virajdilshan2019@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = isHtml

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
