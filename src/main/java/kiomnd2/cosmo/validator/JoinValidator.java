package kiomnd2.cosmo.validator;

import kiomnd2.cosmo.api.AccountApi;
import kiomnd2.cosmo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(AccountApi.Request.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountApi.Request request = (AccountApi.Request) target;
        if (accountRepository.existsByEmail(request.getEmail())) {
            errors.rejectValue("email", "invalid.email", new Object[]{request.getEmail()}, "이미 존재하는 이메일 입니다");
        }

        if (accountRepository.existsByNickname(request.getNickname())) {
            errors.rejectValue("nickname", "invalid.nickname", new Object[]{request.getNickname()}, "이미 존재하는 닉네임 입니다");
        }
    }
}
