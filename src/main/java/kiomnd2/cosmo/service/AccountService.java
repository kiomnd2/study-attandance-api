package kiomnd2.cosmo.service;

import kiomnd2.cosmo.api.AccountApi;
import kiomnd2.cosmo.dto.AccountDto;

public interface AccountService {

    AccountDto createAccount(AccountApi.JoinRequest request);

    AccountDto checkEmailToken(String token, Long id);

    AccountDto getAccount(AccountApi.JoinRequest request);

}
