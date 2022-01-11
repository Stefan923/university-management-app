package com.utcn.universityapp.service;

import com.utcn.universityapp.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AccountService accountService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.getByUsername(username);
        List<GrantedAuthority> authorities = Collections.emptyList();
        return buildUserForAuthentication(account, authorities);
    }

    private UserDetails buildUserForAuthentication(Account account, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(),
                account.isEnabled(), true, true, true, authorities);
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

}
