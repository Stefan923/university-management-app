package com.utcn.universityapp.service;

import com.utcn.universityapp.domain.Account;
import com.utcn.universityapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
public class AccountService {

    private AccountRepository accountRepository;

    public Account getByUsername(String username) throws NoSuchElementException {
        return accountRepository.findByUsernameLike(username)
                .orElseThrow(NoSuchElementException::new);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

}
