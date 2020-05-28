package org.nkv.account.service;

import java.util.Optional;

import org.nkv.account.domain.Account;
import org.nkv.account.domain.User;
import org.nkv.account.exception.EntityNotFoundException;
import org.nkv.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Account getByUser(User user) {
        Optional<Account> optAccount = repository.findById(user.getId());
        if (!optAccount.isPresent()) {
            throw new EntityNotFoundException("Account not found");
        }
        return optAccount.get();
    }
}