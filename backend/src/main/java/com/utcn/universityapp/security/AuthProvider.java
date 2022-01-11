package com.utcn.universityapp.security;

import com.utcn.universityapp.domain.Account;
import com.utcn.universityapp.domain.Attempt;
import com.utcn.universityapp.repository.AttemptRepository;
import com.utcn.universityapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Component
public class AuthProvider implements AuthenticationProvider {

    private static final int ATTEMPTS_LIMIT = 3;

    private PasswordEncoder passwordEncoder;

    private AccountService accountService;

    private AttemptRepository attemptsRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("AuthProvider - authenticate");
        System.out.println(authentication.getName());
        System.out.println(authentication.getCredentials().toString());
        String username = authentication.getName();
        String password = passwordEncoder.encode(authentication.getCredentials().toString());

        Account account;
        try {
            account = accountService.getByUsername(username);
        } catch (NoSuchElementException ex) {
            throw new UsernameNotFoundException("Invalid username or password!");
        }

        if (!account.isAccountNonLocked()) {
            throw new LockedException("Too many invalid attempts. Your account has been locked!");
        }

        if (!account.isEnabled()) {
            throw new LockedException("Your account is waiting for approval.");
        }

        if (account.getUsername().equalsIgnoreCase(username) && account.getPassword().equals(password)) {
            authentication.setAuthenticated(true);
        } else {
            processFailedAttempts(username, account);
        }

        return authentication;
    }

    private void processFailedAttempts(String username, Account account) throws LockedException {
        attemptsRepository.save(new Attempt(username, LocalDateTime.now()));

        int userAttempts = attemptsRepository
                .countAllByUsernameLikeAndAttemptTimeGreaterThanEqual(username, LocalDateTime.now());

        if (userAttempts > ATTEMPTS_LIMIT) {
            account.setLocked(true);
            accountService.save(account);

            throw new LockedException("Too many invalid attempts. Your account has been locked!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setAttemptsRepository(AttemptRepository attemptsRepository) {
        this.attemptsRepository = attemptsRepository;
    }

}
