package kiomnd2.cosmo.service;

import kiomnd2.cosmo.api.AccountApi;
import kiomnd2.cosmo.dto.AccountDto;

import java.util.Optional;

public interface AccountService {

    AccountDto processNewAccount(AccountApi.Request request);


}
