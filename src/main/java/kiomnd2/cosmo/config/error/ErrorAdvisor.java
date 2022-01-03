package kiomnd2.cosmo.config.error;


import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@ControllerAdvice
public class ErrorAdvisor {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessage handlerValidationException(MethodArgumentNotValidException e) {
        return bindingResult(e.getBindingResult());
    }

    private ErrorMessage bindingResult(BindingResult bindingResult) {
        return Optional.ofNullable(bindingResult.getFieldError())
                .map(it -> ErrorMessage.badRequest(it.getDefaultMessage()))
                .orElse(ErrorMessage.badRequest("잘못된 요청입니다."));
    }
}
