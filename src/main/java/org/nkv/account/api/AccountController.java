package org.nkv.account.api;

import org.nkv.account.domain.Account;
import org.nkv.account.domain.User;
import org.nkv.account.exception.InvalidRequestException;
import org.nkv.account.service.AccountService;
import org.nkv.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public AccountController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }
    
    @PostMapping("/account")
    public Account accountHandler(@RequestHeader(name = "Username", required = false) String username) {
        if (username == null) {
            throw new InvalidRequestException("Missing header: Username");
        }
        User user = userService.getByUsername(username);
        return accountService.getByUser(user);
    }
}