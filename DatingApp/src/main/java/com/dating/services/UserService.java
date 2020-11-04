package com.dating.services;

import com.dating.models.users.DatingUser;
import org.springframework.web.context.request.WebRequest;

public class UserService
{
    
    public DatingUser createDatingUser(WebRequest dataFromCreateUserForm)
    {
        DatingUser datingUser = null;
        
        boolean sex = resolveSexInput(dataFromCreateUserForm.getParameter("sexinput"));
        int interestedIn = resolveInterestedInInput(dataFromCreateUserForm.getParameter("interestedininput"));
        
        String password = dataFromCreateUserForm.getParameter("passwordinput");
        String confirmPassword = dataFromCreateUserForm.getParameter("confirmpasswordinput");
    
        if(checkIfPasswordsMatch(password, confirmPassword))
        {
           datingUser = new DatingUser(sex, interestedIn,
                    Integer.parseInt(dataFromCreateUserForm.getParameter("ageinput")),
                    dataFromCreateUserForm.getParameter("usernameinput"),
                    dataFromCreateUserForm.getParameter("emailinput"), password);
        }
    
        return datingUser;
        
    }
    
    
    public static boolean resolveSexInput(String sexInput)
    {
        return sexInput.equals("female");
    }
    public static int resolveInterestedInInput(String interestedInInput)
    {
        int interestedIn = -1;
        if(interestedInInput.equals("males"))
        {
            interestedIn = 0;
        }
        else if(interestedInInput.equals("females"))
        {
            interestedIn = 1;
        }
        else
        {
            interestedIn = 2;
        }
        
        return interestedIn;
    }
    
    public static boolean checkIfPasswordsMatch(String password, String confirmPassword)
    {
        return password.equals(confirmPassword);
    }
    
}
