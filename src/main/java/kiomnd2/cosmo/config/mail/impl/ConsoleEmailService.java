package kiomnd2.cosmo.config.mail.impl;

import kiomnd2.cosmo.config.mail.EmailMessage;
import kiomnd2.cosmo.config.mail.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("local")
@Component
public class ConsoleEmailService implements EmailService {

    @Override
    public void sendEmail(EmailMessage message) {
        log.info("sent email: {}" , message.getMessage());
    }
}
