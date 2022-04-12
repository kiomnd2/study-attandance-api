package kiomnd2.cosmo.config.error;


import kiomnd2.cosmo.controller.EmailCheckController;
import kiomnd2.cosmo.exception.InvalidTokenException;
import kiomnd2.cosmo.exception.NotFoundEmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(assignableTypes = EmailCheckController.class)
public class EmailControllerAdvice {

    @ExceptionHandler(NotFoundEmailException.class)
    public String handlerNotFoundEmailException(NotFoundEmailException e, Model model) {
        log.debug("wrong email");
        model.addAttribute("error", "wrong.email");
        model.addAttribute("errorMessage", "이메일이 정확하지 않습니다.");
        return "account/checked-Email";
    }

    @ExceptionHandler(InvalidTokenException.class)
    public String handlerNotFoundEmailException(InvalidTokenException e, Model model) {
        log.debug("wrong token");
        model.addAttribute("error", "wrong.token");
        model.addAttribute("errorMessage", "잘못된 토큰입니다.");
        return "account/checked-Email";
    }

}
