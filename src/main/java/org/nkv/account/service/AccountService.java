package org.nkv.account.service;

import org.nkv.account.domain.Account;
import org.nkv.account.domain.User;

public interface AccountService {
    
    Account getByUser(User user);
}