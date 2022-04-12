package kiomnd2.cosmo.config.error;


import kiomnd2.cosmo.exception.NotFoundEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class ErrorAdvisor {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessage<String> handlerValidationException(MethodArgumentNotValidException e) {
        return bindingResult(e.getBindingResult());
    }

    private ErrorMessage<String> bindingResult(BindingResult bindingResult) {
        return Optional.ofNullable(bindingResult.getFieldError())
                .map(it -> ErrorMessage.badRequest(it.getDefaultMessage()))
                .orElse(ErrorMessage.badRequest("잘못된 요청입니다."));
    }
}
