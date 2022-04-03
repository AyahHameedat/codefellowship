package com.example.lab16.Controllers;


import com.example.lab16.Models.ApplicationUser;
import com.example.lab16.Repositries.ApplicationUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.sql.Date;

@Controller
public class mainController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ApplicationUserRepo AppUserRepo;


    @GetMapping("/index")
    public String getHomePage()
    {
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage()
    {
        return "login";
    }


    @GetMapping("/signup")
    public String getSignUpPage()
    {
        return "signup";
    }

////this.dateOfBirth = dateOfBirth;
////        this.bio = bio;
//        @GetMapping("test1")
//        public String get( @RequestParam Date dateOfBirth) {
//        return dateOfBirth.toString();
//    }


    @PostMapping("/signup")
    public String createSignUpPage(@RequestParam String password, @RequestParam String username, @RequestParam String firstName, @RequestParam String lastName, @RequestParam Date dateOfBirth, @RequestParam String bio)
    {
//        insert into application_user (bio, date_of_birth, firs_name, last_name, password, username, id) values ($1, $2, $3, $4, $5, $6, $7)
//           public ApplicationUser(String password, String username, String firsName, String lastName, String dateOfBirth, String bio) {
        ApplicationUser AppUser = new ApplicationUser(passwordEncoder.encode(password), username, firstName, lastName, dateOfBirth, bio);
        AppUserRepo.save(AppUser);
        return "login";
    }


    @PostMapping("/logout")
    public RedirectView logOutUserWithSecret(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        session.invalidate();

        return new RedirectView("/login");
    }

}



//        the reason why @DateTimeFormat(pattern = "yyyy-MM-dd") above was not working for me is that the Date parameter type I was using was java.sql.Date instead of java.utils.Date.