package com.awbd.myreviewer.controllers;

import com.awbd.myreviewer.dtos.AccountDTO;
import com.awbd.myreviewer.dtos.ArticleDTO;
import com.awbd.myreviewer.services.AccountService;

import com.awbd.myreviewer.services.security.JpaUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    AccountService accountService;
    JpaUserDetailsService userService;

    public AccountController(AccountService accountService, JpaUserDetailsService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @RequestMapping("")
    public String getAccountsList(Model model) {
        List<AccountDTO> accounts = accountService.findAll();
        model.addAttribute("accounts", accounts);
        return "accountList";
    }

    @GetMapping("/form")
    public String accountForm(Model model) {
        AccountDTO account = new AccountDTO();
        model.addAttribute("account", account);

        // get available roles
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_WRITER");
        roles.add("ROLE_REVIEWER");

        model.addAttribute("roles", roles);

        return "accountForm";
    }

    @PostMapping("/save")
    public String saveAccount(@Valid @ModelAttribute("account") AccountDTO account, BindingResult bindingResult,
                              @ModelAttribute("password") String password) {
        // encode password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        password = passwordEncoder.encode(password);
        // save user
        userService.save(account, password);
        // save account
        accountService.save(account);

        if (bindingResult.hasErrors())
            return "accountForm";

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showAccount(Model model, @PathVariable String id) {
        model.addAttribute("account",
                accountService.findById(Long.valueOf(id)));

        return "/accountInfo";
    }
}
