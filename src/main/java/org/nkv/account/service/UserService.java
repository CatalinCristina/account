package org.nkv.account.service;

import org.nkv.account.domain.User;

public interface UserService {
    
    User getByUsername(String username);
}