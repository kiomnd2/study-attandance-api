package kiomnd2.cosmo.exception;

public class NotMailVerifiedException extends RuntimeException{
    public NotMailVerifiedException() {
        super("이메일이 인증되지 않았습니다");
    }
}
