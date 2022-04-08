package com.example.lab16.Controllers;


import com.example.lab16.Models.ApplicationUser;
import com.example.lab16.Models.PostUsers;
import com.example.lab16.Repositries.ApplicationUserRepo;
import com.example.lab16.Repositries.PostRepo;
import com.example.lab16.Security.UserDetailsServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.PostUpdate;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.sql.Date;
import java.util.List;

@Controller
public class mainController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ApplicationUserRepo AppUserRepo;
    @Autowired
    PostRepo postRepo;

    @Autowired
    UserDetailsServicesImpl UserDetailsServices;


    @GetMapping("/")
    public String getHomePage(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", userDetails.getUsername());
        return "index";
    }


    @GetMapping("/myprofile")
    public String getUserInfo(Model model) {
//        ApplicationUser appUser = AppUserRepo.findById(id).orElseThrow();;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("firsName", AppUserRepo.findByUsername(userDetails.getUsername()).getFirsName());
        model.addAttribute("lastName", AppUserRepo.findByUsername(userDetails.getUsername()).getLastName());
        model.addAttribute("dateOfBirth", AppUserRepo.findByUsername(userDetails.getUsername()).getDateOfBirth());
        model.addAttribute("bio", AppUserRepo.findByUsername(userDetails.getUsername()).getBio());
//        model.addAttribute("firstName", appUser.getFirsName());


        model.addAttribute("postList", AppUserRepo.findByUsername(userDetails.getUsername()).getPost());
        return "profile";
    }


    @GetMapping("/users")
    public String getUsers(Model model) {
//        System.out.println("USER LISTS");
//        ApplicationUser appUser = AppUserRepo.findById(id).orElseThrow();;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("usersList", AppUserRepo.findAll());
//        model.addAttribute("firstName", appUser.getFirsName());
//        System.out.println("USER LISTS");
        return "usersPage";
    }


    @GetMapping("/users/{id}")
    public String getSingleUser(Model model, @PathVariable Long id) {
        ApplicationUser appUser = AppUserRepo.findById(id).orElseThrow();

//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", appUser.getUsername());
        model.addAttribute("firsName", appUser.getFirsName());
        model.addAttribute("lastName", appUser.getLastName());
        model.addAttribute("dateOfBirth", appUser.getDateOfBirth());
        model.addAttribute("bio", appUser.getBio());
        model.addAttribute("postList", appUser.getPost());

        return "Show-User";
    }

    @PostMapping("/postPage")
    public RedirectView postUsers(@ModelAttribute PostUsers posts, String body) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        posts.setAppUser(AppUserRepo.findByUsername(userDetails.getUsername()));

        postRepo.save(posts);

        return new RedirectView("/myprofile");
    }




    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }


    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup";
    }

////this.dateOfBirth = dateOfBirth;
////        this.bio = bio;
//        @GetMapping("test1")
//        public String get( @RequestParam Date dateOfBirth) {
//        return dateOfBirth.toString();
//    }


    @PostMapping("/signup")
    public String createSignUpPage(@RequestParam String password, @RequestParam String username, @RequestParam String firstName, @RequestParam String lastName, @RequestParam Date dateOfBirth, @RequestParam String bio) {
//        insert into application_user (bio, date_of_birth, firs_name, last_name, password, username, id) values ($1, $2, $3, $4, $5, $6, $7)
//           public ApplicationUser(String password, String username, String firsName, String lastName, String dateOfBirth, String bio) {
        ApplicationUser AppUser = new ApplicationUser(passwordEncoder.encode(password), username, firstName, lastName, dateOfBirth, bio);
        AppUserRepo.save(AppUser);
        return "login";
    }


    @PostMapping("/logout")
    public RedirectView logOutUserWithSecret(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return new RedirectView("/login");
    }


// https://www.baeldung.com/spring-boot-custom-error-page
    @RequestMapping("/errors")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            }
        }
        return "errors";
    }



//    /feed

    @GetMapping("/feed")
    public String getPostUsers(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("postList", postRepo.findAll());
//        model.addAttribute("firstName", appUser.getFirsName());
//        System.out.println("USER LISTS");
//
//        ApplicationUser appUser = AppUserRepo.findById(id).orElseThrow();
////        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        model.addAttribute("username", appUser.getUsername());
//        model.addAttribute("postList", appUser.getPost());

        return "/feed";
    }


    @Transactional
    @GetMapping("/follow/{id}")
    String showFollowSuccessScreen(@PathVariable("id") long id, Model model) {

        // user to follow
        ApplicationUser usertofollow = AppUserRepo.findById(id).orElseThrow();

        // get current logged in user username

        // use the user name or ID to find the user by username or ID

        // once you have that object

        // add the curetn logged in user to the following of usertofollow

        // add usertofollow to current logged in user followers

        // update both the userToFollow and the current logged in User to the DB by calling save
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        // HARD CODED EXAMPLE OF IT
        ApplicationUser currentLoggedInUser = AppUserRepo.findByUsername(userDetails.getUsername());
        currentLoggedInUser.getFollowers().add(usertofollow);

        usertofollow.getFollowing().add(currentLoggedInUser);

        AppUserRepo.save(usertofollow);
        AppUserRepo.save(currentLoggedInUser);

        return "/success";
    }


    @GetMapping("/followList/{id}")
    public String getFollowers(Model model, @PathVariable Long id) {
        ApplicationUser appUser = AppUserRepo.findById(id).orElseThrow();

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", userDetails.getUsername());
//        postRepo.findAll()
        return "usersPage";
    }


}

//        the reason why @DateTimeFormat(pattern = "yyyy-MM-dd") above was not working for me is that the Date parameter type I was using was java.sql.Date instead of java.utils.Date.