package org.nkv.account.repository;

import org.nkv.account.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
    
}