package kiomnd2.cosmo.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface PrincipalProvider {

    Principal getPrincipal(Object id);

    @RequiredArgsConstructor
    @Getter
    class Principal {

        /**
         * 값
         */
        private final long value;

        /**
         * 관리자 여부
         */
        private final boolean manager;
    }
}
