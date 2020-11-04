package com.dating.controllers;

import com.dating.models.users.DatingUser;
import com.dating.repositories.UserRepository;
import com.dating.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Controller
public class DatingController
{
    UserService userService = new UserService();
    UserRepository userRepository = new UserRepository();
    
    
    
    
    @GetMapping("/")
    public String index()
    {
        return "index";
    }
    
    
    @GetMapping("/startPage")
    public String startPage()
    {
        return "startpage"; // html
    }
    
    @PostMapping("/postCreateUser")
    public String postTwit(WebRequest dataFromCreateUserForm)
    {
        
        DatingUser datingUser = userService.createDatingUser(dataFromCreateUserForm);
    
        if(datingUser!=null)
        {
            userRepository.addDatingUserToDb(datingUser);
    
            return "redirect:/editProfile";
        }
    
        return "redirect:/";
        
    
    }

}
