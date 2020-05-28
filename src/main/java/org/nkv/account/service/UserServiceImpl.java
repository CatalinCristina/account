package org.nkv.account.service;

import java.util.Optional;

import org.nkv.account.domain.User;
import org.nkv.account.exception.EntityNotFoundException;
import org.nkv.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public User getByUsername(String username) {
        Optional<User> optUser = repository.findByUsername(username);
        if (!optUser.isPresent()) {
            throw new EntityNotFoundException("Account not found");
        }
        return optUser.get();
    }
}