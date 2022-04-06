package kiomnd2.cosmo.config.mail;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class EmailMessage {
    private String to;

    private String subject;

    private String message ;
}
