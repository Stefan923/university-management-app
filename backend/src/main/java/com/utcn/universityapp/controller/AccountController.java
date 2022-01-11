package com.utcn.universityapp.controller;

import com.utcn.universityapp.domain.Account;
import com.utcn.universityapp.domain.user.User;
import com.utcn.universityapp.dto.LoginResponseDTO;
import com.utcn.universityapp.dto.RegisterAccountDTO;
import com.utcn.universityapp.dto.RegisterDetailsDTO;
import com.utcn.universityapp.dto.RegisterUserDTO;
import com.utcn.universityapp.mail.MailSender;
import com.utcn.universityapp.service.AccountService;
import com.utcn.universityapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {

    private AccountService accountService;

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    private final MailSender mailSender = new MailSender();

    @GetMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request, HttpSession session) {
        session.setAttribute(
                "error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION")
        );
        return ResponseEntity.ok(new LoginResponseDTO("e all good", "unknown"));
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

    private String getErrorMessage(HttpServletRequest request, String key) {
        Exception exception = (Exception) request.getSession().getAttribute(key);
        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Invalid username and password!";
        }
        return error;
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

