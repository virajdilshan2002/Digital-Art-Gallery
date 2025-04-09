package lk.viraj.backend.service;

public interface MailService {

    void sendLoggedInEmail(String userName, String toEmail, String subject);

    void sendRegisteredEmail(String name, String email, String subject);
}
