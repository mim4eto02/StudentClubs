package com.softuni.StudentClubs.controller;

import com.softuni.StudentClubs.dto.ContactDto;
import com.softuni.StudentClubs.service.ContactService;
import com.softuni.StudentClubs.service.impl.EmailServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/contact")
public class ContactController {

    private final EmailServiceImpl emailService;

    private final ContactService contactService;


    public ContactController(EmailServiceImpl emailService, ContactService contactService) {
        this.emailService = emailService;
        this.contactService = contactService;
    }

    @GetMapping
    public String contactUs() {
        return "contact";
    }

    @PostMapping
    public String contactUs(@Valid ContactDto contactDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("contactDTO", contactDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contactDTO", bindingResult);

            return "redirect:contact";
        }

        this.emailService.sendContactEmail(contactDTO.getName(), contactDTO.getEmail(),
                contactDTO.getSubject(), contactDTO.getMessage());
        contactService.saveContact(contactDTO);


        return "redirect:/";
    }

    @ModelAttribute("contactDTO")
    public ContactDto contactDto() {
        return new ContactDto();
    }
}
