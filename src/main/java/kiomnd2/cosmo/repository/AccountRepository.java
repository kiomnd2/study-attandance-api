package kiomnd2.cosmo.repository;

import kiomnd2.cosmo.domain.AccountDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountRepository extends JpaRepository<AccountDao, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickName);
}
