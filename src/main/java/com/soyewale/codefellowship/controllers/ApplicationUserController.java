package com.soyewale.codefellowship.controllers;

import com.soyewale.codefellowship.models.ApplicationUser;
import com.soyewale.codefellowship.models.ApplicationUserRepository;
import com.soyewale.codefellowship.models.Post;
import com.soyewale.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/signup")
    public RedirectView createNewApplicationUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio){
       //make new user, salt and has the password
        //passwordEncoder.encode(password) salts and hashes.
        ApplicationUser newUser = new ApplicationUser(username, passwordEncoder.encode(password), firstName, lastName, dateOfBirth, bio);
       //save new user to database.
        applicationUserRepository.save(newUser);
        //send them back home
        return new RedirectView("/");
    }

//    @PostMapping("/login")
//    public RedirectView login(){
//        return new RedirectView(("/");
//    }
    @GetMapping("/login")
    public String showLogInForm(){
        return "login";
    }

    @GetMapping("/users/{id}") //id is dynamic
    public String showUserDetails(@ PathVariable long id, Principal p, Model m){
        ApplicationUser theUser = applicationUserRepository.findById(id).get();  //create an instance of the application user and find and get id
//        m.addAttribute("usernameWeAreVisiting", theUser.getUsername());  //passs that as the value of the username key.
//        m.addAttribute("userIdWeAreVisiting", theUser.id);
        m.addAttribute("userWeAreVisiting", theUser);
//        m.addAttribute("principalTheAndroid", p.getName()); //get name of who is logged in.
        return "userDetails";

    }

    @GetMapping("/myProfile")
    public String showMyProfile(Principal p, Model m){
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("loggedInUser", user); // the user has all the attributes of the application user.
        return "myProfile";
    }

    @PostMapping("/myProfile")
    public RedirectView createPost(Principal p, Model m, String body, boolean appropriate){
        String now = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
        Post newPost = new Post(user, body, now, appropriate);
        postRepository.save(newPost);
        return new RedirectView("myProfile");

    }

//    @PostMapping()

    @PostMapping("/followMe/{id}")
    public RedirectView follow(@PathVariable long id, Principal p){
        ApplicationUser loggedInUser = applicationUserRepository.findByUsername(p.getName());
        ApplicationUser toBeFollowed = applicationUserRepository.findById(id).get();
        loggedInUser.usersFollowingMe.add(toBeFollowed);
        applicationUserRepository.save(toBeFollowed);
        return new RedirectView("/users/" + toBeFollowed.getId());
    }
}
