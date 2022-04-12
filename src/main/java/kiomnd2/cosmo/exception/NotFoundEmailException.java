package kiomnd2.cosmo.exception;

public class NotFoundEmailException extends RuntimeException{

    public NotFoundEmailException() {
        super("이메일을 찾을 수 없습니다");
    }
}
