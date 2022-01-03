package kiomnd2.cosmo.config.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorMessage<T> {

    // 에러 코드
    private final int code;

    private final T message;

    public ErrorMessage(int code, T message) {
        this.code = code;
        this.message = message;
    }

    public static  <T> ErrorMessage<T> of(HttpStatus status, T message) {
        return new ErrorMessage<T>(status.value(), message);
    }

    public static <T> ErrorMessage<T> badRequest(T message) {
        return new ErrorMessage<T>(HttpStatus.BAD_REQUEST.value(), message);
    }

}
