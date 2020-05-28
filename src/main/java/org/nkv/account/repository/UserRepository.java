package org.nkv.account.repository;

import java.util.Optional;

import org.nkv.account.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
}