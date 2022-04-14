package kiomnd2.cosmo.config.security;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 인증 주체를 받아오는 인터페이스
 *
 * @author antop
 */
public interface PrincipalProvider {

    /**
     * 인증 주체를 얻어온다.
     *
     * @param id 고유 식별자
     * @return 인증 주체
     */
    Principal getPrincipal(Object id);

    @RequiredArgsConstructor
    @Getter
    @ToString
    class Principal {
        /**
         * Principal 값
         */
        private final long value;
        /**
         * 관리자 여부
         */
        private final boolean manager;
    }

}
