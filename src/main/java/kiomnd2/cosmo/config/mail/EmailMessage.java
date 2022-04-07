package kiomnd2.cosmo.config.mail;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class EmailMessage {
    private String to;

    private String subject;

    private String message;
}
