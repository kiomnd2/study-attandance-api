package kiomnd2.cosmo.service.impl;

import kiomnd2.cosmo.api.AccountApi;
import kiomnd2.cosmo.dto.AccountDto;
import kiomnd2.cosmo.service.AccountService;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public AccountDto processNewAccount(AccountApi.Request request) {
        return null;
    }
}
