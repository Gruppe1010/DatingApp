package com.dating.services;

import com.dating.models.users.DatingUser;
import org.springframework.web.context.request.WebRequest;

public class UserService
{
    
    public DatingUser createDatingUser(WebRequest dataFromCreateUserForm)
    {
        DatingUser datingUser = null;
        
        boolean sex = resolveSexInput(dataFromCreateUserForm.getParameter("sexinput"));
        String password = dataFromCreateUserForm.getParameter("passwordinput");
        String confirmPassword = dataFromCreateUserForm.getParameter("confirmpasswordinput");
    
        if(checkIfPasswordsMatch(password, confirmPassword))
        {
           datingUser = new DatingUser(sex,
                    Integer.parseInt(dataFromCreateUserForm.getParameter("interestedininput")),
                    Integer.parseInt(dataFromCreateUserForm.getParameter("ageinput")),
                    dataFromCreateUserForm.getParameter("usernameinput"),
                    dataFromCreateUserForm.getParameter("emailinput"), password);
        }
    
        return datingUser;
        
    }
    
    
    public static boolean resolveSexInput(String string)
    {
        return string.equals("female");
    }
    
    public static boolean checkIfPasswordsMatch(String password, String confirmPassword)
    {
        return password.equals(confirmPassword);
    }
    
}
