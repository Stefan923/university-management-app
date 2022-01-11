package com.utcn.universityapp.service;

import com.utcn.universityapp.domain.Account;
import com.utcn.universityapp.repository.AccountRepository;
import com.utcn.universityapp.util.SessionGenerator;
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

    public String createSession(String username) throws NoSuchElementException {
        Account account = accountRepository.findByUsernameLike(username)
                .orElseThrow(NoSuchElementException::new);

        String session = SessionGenerator.generateSession();
        account.setSession(session);
        accountRepository.save(account);

        return session;
    }

    public boolean removeSession(String username, String password) {
        return accountRepository.findByUsernameLike(username)
                .orElseThrow(NoSuchElementException::new)
                .getPassword().equals(password);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

}
