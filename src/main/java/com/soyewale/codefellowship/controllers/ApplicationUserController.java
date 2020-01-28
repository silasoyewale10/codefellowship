package com.soyewale.codefellowship.controllers;

import com.soyewale.codefellowship.models.ApplicationUser;
import com.soyewale.codefellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public RedirectView createNewApplicationUser(String username, String password, String firstName, String lastname, int dateOfBirth, String bio){
       //make new user, salt and has the password
        //passwordEncoder.encode(password) salts and hashes.
        ApplicationUser newUser = new ApplicationUser(username, passwordEncoder.encode(password), firstName, lastname, dateOfBirth, bio);
       //save new user to database.
        applicationUserRepository.save(newUser);
        //send them back home
        return new RedirectView("/");
    }

//    @PostMapping("/login")
//    public RedirectView login(){
//        return new RedirectView(("/");
//    }
    @GetMapping("login")
    public String showLogInForm(){
        return "login";
    }
}
