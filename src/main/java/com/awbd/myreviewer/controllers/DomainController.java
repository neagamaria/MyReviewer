package com.awbd.myreviewer.controllers;

import com.awbd.myreviewer.domain.Level;
import com.awbd.myreviewer.dtos.ArticleDTO;
import com.awbd.myreviewer.dtos.DomainDTO;
import com.awbd.myreviewer.services.ArticleService;
import com.awbd.myreviewer.services.DomainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/domains")
public class DomainController {
    DomainService domainService;
    ArticleService articleService;

    public DomainController(DomainService domainService, ArticleService articleService) {
        this.domainService = domainService;
        this.articleService = articleService;
    }

    // get all domains
    @RequestMapping("")
    public String domainList(Model model) {
        List<DomainDTO> domains = domainService.findAll();
        model.addAttribute("domains",domains);
        return "domainList";
    }

    // form to add a domain
    @GetMapping("/form")
    public String domainForm(Model model) {
        DomainDTO domain = new DomainDTO();
        model.addAttribute("domain", domain);

        return "domainForm";
    }

    // add new domain
    @PostMapping("/save")
    public String saveDomain(@ModelAttribute DomainDTO domain, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return "domainForm";
        }

        domainService.save(domain);

        return "redirect:/domains";
    }

    // delete domain
    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable String id){
        domainService.deleteById(Long.valueOf(id));
        return "redirect:/domains";
    }
}
