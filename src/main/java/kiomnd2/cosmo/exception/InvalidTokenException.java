package kiomnd2.cosmo.exception;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException() {
        super("잘못된 토큰입니다.");
    }
}
