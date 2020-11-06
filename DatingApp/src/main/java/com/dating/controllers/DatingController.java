package com.dating.controllers;

import com.dating.models.users.DatingUser;
import com.dating.models.users.User;
import com.dating.repositories.UserRepository;
import com.dating.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Controller
public class DatingController
{
    User loggedInUser = null;
    UserService userService = new UserService();
    UserRepository userRepository = new UserRepository();
    
    
    
    /**
     * Returnerer index-html-side ved /-request
     *
     * @return String html-siden
     */
    @GetMapping("/")
    public String index()
    {
        return "index";
    }
    
    @GetMapping("/logIn")
    public String logIn()
    {
        return "login"; // html
    }
    
    @GetMapping("/startPage")
    public String startPage()
    {
        return "startpage"; // html
    }
    
    @GetMapping("/chatPage")
    public String chatPage()
    {
        return "chatpage"; // html
    }
    
    @GetMapping("/candidatePage")
    public String candidatePage()
    {
        return "candidatepage"; // html
    }
    
    @GetMapping("/searchPage")
    public String searchPage()
    {
        return "searchpage"; // html
    }

    @GetMapping("/editProfile")
    public String editProfile(Model datingUserModel)
    {
        datingUserModel.addAttribute("loggedInUser", loggedInUser);
        
        
        return "editprofile"; // html
    }
    
    @GetMapping("/editProfileConfirmation")
    public String editProfileConfirmation()
    {
        
        return "editprofileconfirmation"; // html
    }
    
    
    // I PostMappingens "/" SKAL der stå "post" FØRST! : fx IKKE "/createUser" men "/postCreateUser"
    @PostMapping("/postCreateUser")
    public String postCreateUser(WebRequest dataFromCreateUserForm)
    {
        DatingUser datingUser = userService.createDatingUser(dataFromCreateUserForm);
    
        if(datingUser!=null)
        {
            userRepository.addDatingUserToDb(datingUser);
    
            return "redirect:/editProfile";
        }
    
        return "redirect:/";
    }
    
    @PostMapping("/postLogIn")
    public String postLogIn(WebRequest dataFromLogInForm)
    {
        User user = null;
    
        user = userRepository.checkIfUserExists(dataFromLogInForm);
        loggedInUser = user;
        
        if(user!=null) // hvis der ER blevet gemt en bruger i loggedInUser
        {
            userRepository.setLoggedInUserToNull(); // loggedInUser-attributten i UserRepository
            if(user.isAdmin()) // hvis det er en admin:
            {
                return "redirect:/startPageAdmin"; // url
            }
            // ellers er det en datingUser
            System.out.println(user.isDatingUser());
            return "redirect:/startPage"; // url
        }
        // hvis loggedInUser altså er null
        return "redirect:/logIn"; // url
    }
    
    @PostMapping("/postEditProfile")
    public String postEditProfile(WebRequest dataFromEditProfileForm)
    {
        boolean isProfileEditted = userRepository.checkIfProfileWasEditted(dataFromEditProfileForm);
        
        // hvis checkIfChangesWereMade == true
        
        // updateUser()
        
        if(true)
        {
            return "redirect:/editProfileConfirmation"; // url
        }
    
    
        return "redirect:/editProfile"; // url
    }
    
    
    
    
    
}
