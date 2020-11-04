package com.dating.repositories;

import com.dating.models.users.DatingUser;

import java.sql.*;

public class UserRepository
{
    Connection connection = null;
    
    public Connection establishConnection()
    {
        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lovestruck" +
                                                             "?serverTimezone=UTC", "gruppe10", "gruppe10");
        }
        catch(SQLException e)
        {
            System.out.println("Error in establishConnection: " + e.getMessage());
        }
        
        return connection;
    }
    
    
    public boolean addDatingUserToDb(DatingUser datingUser)
    {
    
        try
        {
            establishConnection();
        
            String sqlCommand = "INSERT into dating_users(blacklisted, sex, interested_in, age, username, email, " +
                                        "password) " +
                                        "values (?,?,?,?,?,?,?);";
            
            // det er vores SQL sætning som vi beder om at få prepared til at blive sendt til databasen:
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
            
            preparedStatement.setInt(1, datingUser.convertBooleanToInt(datingUser.isBlacklisted()));
            preparedStatement.setInt(2, datingUser.convertBooleanToInt(datingUser.getSex()));
            preparedStatement.setInt(3, datingUser.getInterestedIn());
            preparedStatement.setInt(4, datingUser.getAge());
            preparedStatement.setString(5, datingUser.getUsername());
            preparedStatement.setString(6, datingUser.getEmail());
            preparedStatement.setString(7, datingUser.getPassword());
            
            preparedStatement.executeUpdate();
            
            // nu hvor user er blevet oprettet, tilføjer vi databasens genererede id_dating_user til datingUser-objektet
            int idDatingUser = retrieveDatingUserIdFromDb(datingUser);
            
            if(idDatingUser!=-1)
            {
                datingUser.setIdDatingUser(idDatingUser);
            }
            
            return true;
        }
        catch(SQLException e)
        {
            System.out.println("Error in addDatingUserToDb: " + e.getMessage());
            return false;
        }
    }
    
    
    
    public int retrieveDatingUserIdFromDb(DatingUser datingUser)
    {
        establishConnection();
        
        int idDatingUser = -1;
        
        try
        {
            String sqlCommand = "SELECT id_dating_user FROM dating_users WHERE username like ?";
        
            // det er vores SQL sætning som vi beder om at få prepared til at blive sendt til databasen:
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
        
            preparedStatement.setString(1, "%" + datingUser.getUsername() + "%");
            
            idDatingUser = preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            System.out.println("Error in addDatingUserToDb: " + e.getMessage());
        }
        
        return idDatingUser;
    }
    
}
