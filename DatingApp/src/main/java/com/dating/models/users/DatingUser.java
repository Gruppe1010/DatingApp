package com.dating.models.users;

import com.dating.models.PostalInfo;
import com.dating.models.users.User;

import java.util.ArrayList;

public class DatingUser extends User
{
    private boolean sex; // false == mænd, true == kvinder
    private int interestedIn; // 1 == mænd, 2 == kvinder, 3 == begge køn
    private int age;
    // TODO private Image profilePicture;
    private String description;
    private ArrayList<String> tags;
    private PostalInfo postalInfo;
    private ArrayList<DatingUser> kandidatListe;
    
    public DatingUser(){}
    
    public DatingUser(boolean sex, int interestedIn, int age, String username, String email, String password)
    {
        super(username, email, password); // den kalder superklassens constructor
        this.sex = sex;
        this.interestedIn = interestedIn;
        this.age = age;
    
        // TODO profilePicture = null;
        description = null;
        tags = null;
        postalInfo = null;
        kandidatListe = null;
    }
    
    public void editUserInfo()
    {
    
    }
    
    public User createDatingUser()
    {
        // lav user-objekt
        // tilføj user-obj til database
        // findUserId(user-objekt) == finder lige id'et på det nye user-objekt
        // opretKandidatliste(idDatingUser);
        
        return new DatingUser();
    }
    
    
}





/*
// lav seperate metoder til at finde relevant == username, email, password




public void opretKandidatListe(int idDatingUser)
{
    // sql-command: "create table kandidatList" + idDatingUser
}

public void tilføjBrugerTilKandidatListe(int idDatingUser, User targetUser)
{
    // findUserId(targetUser)
    // sql: "insert ? into ?"
    
    // 1.? == idTargetUser
    // 2.? == KandidatList+idDatingUser (== navnet på kandidatlisten)

public int findUserId(User user)
{
    // find idDatingUser på user-objekt
    // return
}

 */

 
