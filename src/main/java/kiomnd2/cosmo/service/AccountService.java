package kiomnd2.cosmo.service;

import kiomnd2.cosmo.api.AccountApi;
import kiomnd2.cosmo.dto.AccountDto;

public interface AccountService {

    AccountDto getAccount(AccountApi.JoinRequest request);

    AccountDto checkEmailToken(String token, String email);
}
