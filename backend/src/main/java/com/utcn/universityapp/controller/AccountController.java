package com.utcn.universityapp.controller;

import com.utcn.universityapp.domain.Account;
import com.utcn.universityapp.domain.Attempt;
import com.utcn.universityapp.domain.user.User;
import com.utcn.universityapp.dto.*;
import com.utcn.universityapp.mail.MailSender;
import com.utcn.universityapp.service.AccountService;
import com.utcn.universityapp.service.AttemptService;
import com.utcn.universityapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {

    private static final String INVALID_SESSION = "INVALID";

    private static final String LOGIN_MESSAGE_SUCCESS = "You have logged in successfully.";
    private static final String LOGIN_MESSAGE_INVALID = "Invalid username or password!";
    private static final String LOGIN_MESSAGE_LOCKED = "Too many invalid attempts. Your account has been locked!";
    private static final String LOGIN_MESSAGE_WAITING_APPROVAL = "Your account is waiting for approval.";
    private static final String REGISTER_MESSAGE_FAILED_TO_SEND_EMAIL = "Couldn't send the confirmation mail.";

    private static final int ATTEMPTS_TIME_INTERVAL_HOURS = 24;
    private static final int MAXIMUM_ATTEMPTS_COUNT = 3;

    private final MailSender mailSender = new MailSender();

    private AccountService accountService;
    private AttemptService attemptService;
    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginAccountDTO credentials) {
        return ResponseEntity.ok(processAuthentication(credentials.getUsername(), credentials.getPassword()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDetailsDTO registerDetails) {
        RegisterAccountDTO account = registerDetails.getAccount();
        RegisterUserDTO user = registerDetails.getUser();

        User createdUser = userService.registerUser(getUserFromRegisterUserDTO(user));

        Account newAccount = getAccountFromRegisterAccountDTO(account);
        newAccount.setUser(createdUser);

        try {
            accountService.save(newAccount);
            mailSender.sendEmailConfirmationMail(newAccount.getEmail());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(HttpStatus.IM_USED);
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.ok(REGISTER_MESSAGE_FAILED_TO_SEND_EMAIL);
        }
    }

    private User getUserFromRegisterUserDTO(RegisterUserDTO registerUserDTO) {
        User user = new User();
        user.setFirstName(registerUserDTO.getFirstName());
        user.setLastName(registerUserDTO.getLastName());
        user.setDateOfBirth(registerUserDTO.getDateOfBirth());
        user.setPhoneNumber(registerUserDTO.getPhoneNumber());
        user.setRoleId(registerUserDTO.getRoleId());

        return user;
    }

    private Account getAccountFromRegisterAccountDTO(RegisterAccountDTO registerAccountDTO) {
        Account account = new Account();
        account.setEmail(registerAccountDTO.getEmail());
        account.setUsername(registerAccountDTO.getUsername());
        account.setPassword(passwordEncoder.encode(registerAccountDTO.getPassword()));

        return account;
    }

    private LoginResultDTO processAuthentication(String username, String password) {
        try {
            Account account = accountService.getByUsername(username);

            if (account.isLocked()) {
                return new LoginResultDTO(false, INVALID_SESSION, LOGIN_MESSAGE_LOCKED);
            }

            if (!passwordEncoder.matches(password, account.getPassword())) {
                attemptService.save(new Attempt(username, LocalDateTime.now()));

                if (attemptService.countAttempts(username,
                        LocalDateTime.now().minusDays(ATTEMPTS_TIME_INTERVAL_HOURS)) >= MAXIMUM_ATTEMPTS_COUNT) {
                    account.setLocked(true);
                    accountService.save(account);

                    return new LoginResultDTO(false, INVALID_SESSION, LOGIN_MESSAGE_LOCKED);
                }

                return new LoginResultDTO(false, INVALID_SESSION, LOGIN_MESSAGE_INVALID);
            }

            if (!account.isEnabled()) {
                return new LoginResultDTO(false, INVALID_SESSION, LOGIN_MESSAGE_WAITING_APPROVAL);
            }

            return new LoginResultDTO(true, accountService.createSession(username), LOGIN_MESSAGE_SUCCESS);
        } catch (NoSuchElementException ex) {
            return new LoginResultDTO(false, INVALID_SESSION, LOGIN_MESSAGE_INVALID);
        }
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setAttemptService(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}

