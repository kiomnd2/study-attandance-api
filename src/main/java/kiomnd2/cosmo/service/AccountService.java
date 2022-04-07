package kiomnd2.cosmo.service;

import kiomnd2.cosmo.api.AccountApi;
import kiomnd2.cosmo.dto.AccountDto;

public interface AccountService {

    AccountDto processNewAccount(AccountApi.JoinRequest request);
}
