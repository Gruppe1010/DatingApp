package com.dating.models.users;

import java.util.ArrayList;

public class Admin extends User
{
    
    private ArrayList<DatingUser> blacklistedUsersList;
    
    public Admin(){}
    public Admin(String username, String email, String password)
    {
        super(username, email, password); // den kalder superklassens constructor
        
        blacklistedUsersList = null;
    }
    
    
    public User createAdmin()
    {
        // lav user-objekt
        // tilføj user-obj til database
        // findUserId(user-objekt) == finder lige id'et på det nye user-objekt
        // opretKandidatliste(idDatingUser);
        
        return new Admin("hej", "hej", "hej");
    }
    
    
    
    
}
