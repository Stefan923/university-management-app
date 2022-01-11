package com.utcn.universityapp.controller;

import com.utcn.universityapp.domain.Account;
import com.utcn.universityapp.domain.user.User;
import com.utcn.universityapp.dto.*;
import com.utcn.universityapp.mail.MailSender;
import com.utcn.universityapp.service.AccountService;
import com.utcn.universityapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {

    public static final String INVALID_SESSION = "INVALID";

    public static final String LOGIN_MESSAGE_SUCCESS = "You have logged in successfully.";
    public static final String LOGIN_MESSAGE_INVALID = "Invalid username or password!";
    public static final String LOGIN_MESSAGE_LOCKED = "Too many invalid attempts. Your account has been locked!";
    public static final String LOGIN_MESSAGE_WAITING_APPROVAL = "Your account is waiting for approval.";

    private final MailSender mailSender = new MailSender();

    private AccountService accountService;
    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginAccountDTO credentials) {
        return ResponseEntity.ok(processAuthentication(credentials.getUsername(), credentials.getPassword()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDetailsDTO registerDetails) {
        System.out.println(registerDetails);
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
            return ResponseEntity.ok("Couldn't send the confirmation mail.");
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

            if (!account.getPassword().equals(passwordEncoder.encode(password))) {
                return new LoginResultDTO(false, INVALID_SESSION, LOGIN_MESSAGE_INVALID);
            }

            if (!account.isEnabled()) {
                return new LoginResultDTO(false, INVALID_SESSION, LOGIN_MESSAGE_WAITING_APPROVAL);
            }

            if (!account.isLocked()) {
                return new LoginResultDTO(false, INVALID_SESSION, LOGIN_MESSAGE_LOCKED);
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
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}

