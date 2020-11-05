package com.dating.models.users;

import com.dating.models.PostalInfo;
import com.dating.models.users.User;

import java.util.ArrayList;

public class DatingUser extends User
{
    private int idDatingUser;
    private boolean blacklisted; // 0 == false, 1 == true
    private boolean sex; // false == mænd, true == kvinder
    private int interestedIn; // 0 == mænd, 1 == kvinder, 2 == begge køn
    private int age;
    // TODO private Image profilePicture;
    private String description;
    private ArrayList<String> tags;
    private PostalInfo postalInfo;
    private ArrayList<DatingUser> kandidatListe;
    
    // constructors
    public DatingUser(){}
    public DatingUser(boolean sex, int interestedIn, int age, String username, String email, String password)
    {
        super(username, email, password); // den kalder superklassens constructor
        this.blacklisted = false;
        this.sex = sex;
        this.interestedIn = interestedIn;
        this.age = age;
    
        // TODO profilePicture = null;
        description = null;
        tags = null;
        postalInfo = null;
        kandidatListe = null;
    }
    
    // getters + setters
    public String getUsername()
    {
        return super.getUsername();
    }
    public void setUsername(String username)
    {
        super.setUsername(username);
    }
    public String getEmail()
    {
        return super.getEmail();
    }
    public void setEmail(String email)
    {
        super.setEmail(email);
    }
    public String getPassword()
    {
        return super.getPassword();
    }
    public void setPassword(String password)
    {
        super.setPassword(password);
    }
    
    public int getIdDatingUser()
    {
        return idDatingUser;
    }
    public void setIdDatingUser(int idDatingUser)
    {
        this.idDatingUser = idDatingUser;
    }
    public boolean isBlacklisted()
    {
        return blacklisted;
    }
    public void setBlacklisted(boolean blacklisted)
    {
        this.blacklisted = blacklisted;
    }
    public boolean getSex()
    {
        return sex;
    }
    public void setSex(boolean sex)
    {
        this.sex = sex;
    }
    public int getInterestedIn()
    {
        return interestedIn;
    }
    public void setInterestedIn(int interestedIn)
    {
        this.interestedIn = interestedIn;
    }
    public int getAge()
    {
        return age;
    }
    public void setAge(int age)
    {
        this.age = age;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public ArrayList<String> getTags()
    {
        return tags;
    }
    public void setTags(ArrayList<String> tags)
    {
        this.tags = tags;
    }
    public PostalInfo getPostalInfo()
    {
        return postalInfo;
    }
    public void setPostalInfo(PostalInfo postalInfo)
    {
        this.postalInfo = postalInfo;
    }
    public ArrayList<DatingUser> getKandidatListe()
    {
        return kandidatListe;
    }
    public void setKandidatListe(ArrayList<DatingUser> kandidatListe)
    {
        this.kandidatListe = kandidatListe;
    }
    
    
    // andre metoder
    /**
     * Konverterer boolean til integer-værdi
     *
     * @param booleanInput Bool'en som skal konverteres
     
     * @return int Den konverterede boolean
     */
    public int convertBooleanToInt(Boolean booleanInput)
    {
        int convertedBoolean = 0; // convertedBoolean er nu sat til false - 0 == false
        
        if(booleanInput==true)
        {
            convertedBoolean = 1;
        }
        
        return convertedBoolean;
    }

    
    
    public void editUserInfo()
    {
    
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

 
