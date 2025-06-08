package com.awbd.myreviewer.controllers;

import com.awbd.myreviewer.services.AccountService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.Objects;

@Controller
public class MainController {
    private final AccountService accountService;

    public MainController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping({"", "/", "/home"})
    public String getHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("currentUser", auth.getName());
        model.addAttribute("currentRole", auth.getAuthorities().iterator().next().getAuthority());


        if(Objects.equals(auth.getName(), "anonymousUser")) {
            return "login";
        }

        Long accountId = accountService.findByName(auth.getName()).getId();
        model.addAttribute("accountId", accountId);

        return "main";
    }

    @GetMapping("/login")
    public String showLogInForm(){ return "login"; }

    @GetMapping("/access_denied")
    public String accessDeniedPage(){ return "accessDenied"; }
}
