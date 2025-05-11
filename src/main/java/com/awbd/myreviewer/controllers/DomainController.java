package com.awbd.myreviewer.controllers;

import com.awbd.myreviewer.dtos.DomainDTO;
import com.awbd.myreviewer.services.ArticleService;
import com.awbd.myreviewer.services.DomainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/domains")
public class DomainController {
    DomainService domainService;

    public DomainController(DomainService domainService) {
        this.domainService = domainService;
    }

    @RequestMapping("")
    public String domainList(Model model) {
        List<DomainDTO> domains = domainService.findAll();
        model.addAttribute("domains",domains);
        return "domainList";
    }
}
