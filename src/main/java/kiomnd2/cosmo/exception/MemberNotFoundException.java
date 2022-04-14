package kiomnd2.cosmo.exception;

public class MemberNotFoundException extends RuntimeException{

    public MemberNotFoundException() {
        super("멤버를 찾을 수 없습니다");
    }
}
