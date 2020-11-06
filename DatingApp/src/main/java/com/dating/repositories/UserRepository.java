package com.dating.repositories;

import com.dating.models.users.Admin;
import com.dating.models.users.DatingUser;
import com.dating.models.users.User;
import org.springframework.web.context.request.WebRequest;

import java.sql.*;

public class UserRepository
{
    Connection lovestruckConnection = null;
    Connection favouriteslistConnection = null;
    
    User loggedInUser = new User();
    DatingUser loggedInDatingUser = new DatingUser();
    Admin loggedInAdmin = new Admin();
    
    
    /**
     * Laver en connection til lovestruck-databasen
     *
     * @param dbName Navnet på db som vi connecter til
     *
     * @return Connection Den oprettede connection ELLER null ved fejl i oprettelsen af connection
     */
    public Connection establishConnection(String dbName)
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ dbName +
                                                                       "?serverTimezone=UTC", "gruppe10", "gruppe10");
        }
        catch(SQLException e)
        {
            System.out.println("Error in establishConnection: " + e.getMessage());
        }
        
        return connection;
    }
    
    /**
     * Tilføjer DatingUser-objekt til dating_users-tabellen i db
     * OG sætter dets id-attribut
     * OG opretter ny favourites_list-tabel i db knyttet til brugeren
     *
     * @param datingUser
     *
     * @return boolean Om det lykkedes eller ej
     */
    public boolean addDatingUserToDb(DatingUser datingUser)
    {
    
        try
        {
            lovestruckConnection = establishConnection("lovestruck");
        
            String sqlCommand = "INSERT into dating_users(blacklisted, sex, interested_in, age, username, email, " +
                                        "password) " +
                                        "values (?,?,?,?,?,?,?);";
            
            // det er vores SQL sætning som vi beder om at få prepared til at blive sendt til databasen:
            PreparedStatement preparedStatement = lovestruckConnection.prepareStatement(sqlCommand);
            
            preparedStatement.setInt(1, datingUser.convertBooleanToInt(datingUser.isBlacklisted()));
            preparedStatement.setInt(2, datingUser.convertBooleanToInt(datingUser.getSex()));
            preparedStatement.setInt(3, datingUser.getInterestedIn());
            preparedStatement.setInt(4, datingUser.getAge());
            preparedStatement.setString(5, datingUser.getUsername());
            preparedStatement.setString(6, datingUser.getEmail());
            preparedStatement.setString(7, datingUser.getPassword());
            
            // user tilføjes til database
            preparedStatement.executeUpdate();
            
            // nu hvor user er blevet oprettet, tilføjer vi databasens genererede id_dating_user til datingUser-objektet
            int idDatingUser = retrieveDatingUserIdFromDb(datingUser);
            
            if(idDatingUser!=-1)
            {
                // setter id'et, hvis det er genereret korrekt
                datingUser.setIdDatingUser(idDatingUser);
                // genererer en favourites_list tabel i databasen - knyttet til user-entitet via id_dating_user
                // fx til en user med id_dating_user 3 oprettes tabellen: favourites_list_3
                createFavouritesListTableDb(idDatingUser);
            }
            
            return true;
        }
        catch(SQLException e)
        {
            System.out.println("Error in addDatingUserToDb: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Opretter ny favourites_list tabel ud fra idDatingUser-int
     *
     * @param idDatingUser id'et som skal være i db-tabellens navn: fx favourites_list_3
     *
     * @return void
     */
    public void createFavouritesListTableDb(int idDatingUser)
    {
        try
        {
            favouriteslistConnection = establishConnection("lovestruck_favourites_list");
    
            // lovestruc_favourites_list == den database som vi laver tabellen i
            // favourites_list_? == navnet på tabellen
            // id_dating_user INT NOT NULL,== navn på ny kolonne og hvilke bokse der er krydset af
            // PRIMARY KEY(id_dating_user) == siger at det er kolonnen id_dating_user som er primary key
            String sqlCommand = "CREATE TABLE lovestruck_favourites_list.favourites_list_? (id_dating_user INT NOT NULL, PRIMARY " +
                                        "KEY (id_dating_user));";
    
            PreparedStatement preparedStatement = favouriteslistConnection.prepareStatement(sqlCommand);
    
            preparedStatement.setInt(1, idDatingUser);
    
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            System.out.println("Error in createFavouritesListTableDb: " + e.getMessage());
        }
        
    }
    
    public int retrieveDatingUserIdFromDb(DatingUser datingUser)
    {
        lovestruckConnection = establishConnection("lovestruck");
        
        int idDatingUser = -1;

        try
        {
            String sqlCommand = "SELECT * FROM dating_users WHERE username like ?";
        
            // det er vores SQL sætning som vi beder om at få prepared til at blive sendt til databasen:
            PreparedStatement preparedStatement = lovestruckConnection.prepareStatement(sqlCommand);
        
            preparedStatement.setString(1, "%" + datingUser.getUsername() + "%");

            ResultSet resultSet = preparedStatement.executeQuery();


            // TODO Find ud af hvorfor vi skal skrive next
            if(resultSet.next())
            {
                idDatingUser = resultSet.getInt(1);
            }



            //System.out.println(idDatingUser);
        }
        catch(SQLException e)
        {
            System.out.println("Error in retrieveIdDatingUserFromDb: " + e.getMessage());
        }
        
        return idDatingUser;
    }
    
    public boolean isUsernameAvailable(String username)
    {
        lovestruckConnection = establishConnection("lovestruck");
    
        boolean usernameIsAvailable = true; // sættes til at være available by default
    
        try
        {
            String sqlCommand = "SELECT * FROM dating_users WHERE username = ?";
        
            // det er vores SQL sætning som vi beder om at få prepared til at blive sendt til databasen:
            PreparedStatement preparedStatement = lovestruckConnection.prepareStatement(sqlCommand);
        
            preparedStatement.setString(1, username);
       
            ResultSet resultSet = preparedStatement.executeQuery();
        
            // TODO Find ud af hvorfor vi skal skrive next
            if(resultSet.next())
            {
                usernameIsAvailable = false;
            }
        }
        catch(SQLException e)
        {
            System.out.println("Error in isUsernameAvailable: " + e.getMessage());
        }
    
        return usernameIsAvailable;
    }
    
    public boolean isEmailAvailable(String email)
    {
        lovestruckConnection = establishConnection("lovestruck");
    
        boolean emailIsAvailable = true; // sættes til at være available by default
    
        try
        {
            String sqlCommand = "SELECT * FROM dating_users WHERE email = ?";
        
            // det er vores SQL sætning som vi beder om at få prepared til at blive sendt til databasen:
            PreparedStatement preparedStatement = lovestruckConnection.prepareStatement(sqlCommand);
        
            preparedStatement.setString(1, email);
        
            ResultSet resultSet = preparedStatement.executeQuery();
        
            // TODO Find ud af hvorfor vi skal skrive next
            if(resultSet.next())
            {
                emailIsAvailable = false;
            }
        }
        catch(SQLException e)
        {
            System.out.println("Error in isEmailAvailable: " + e.getMessage());
        }
    
        return emailIsAvailable;
    }
    
    public User checkIfUserExists(WebRequest dataFromLogInForm)
    {
        lovestruckConnection = establishConnection("lovestruck");
        
        try
        {
            ResultSet resultSet = findUserInDb(dataFromLogInForm, "admins");
            
            if(resultSet.next()) // hvis det er en admin
            {
                loggedInUser = loggedInAdmin;
            }
            else // når det ikke er en admin, så tjekker vi om det er en datingUser
            {
                resultSet = findUserInDb(dataFromLogInForm, "dating_users");
    
                if(resultSet.next()) // hvis det er en datingUser
                {
                    loggedInUser = loggedInDatingUser;
                }
            }
            if(resultSet!=null) // hvis den IKKE er null indeholder den altså ENTEN en admin ELLER en datingUser
            {
                loggedInUser.setUsername(resultSet.getString(2));
                loggedInUser.setEmail(resultSet.getString(3));
                loggedInUser.setPassword(resultSet.getString(4));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Error in isEmailAvailable: " + e.getMessage());
        }
    
        return loggedInUser;
    }
    
    
    public ResultSet findUserInDb(WebRequest dataFromLogInForm, String table)
    {
        ResultSet resultSet = null;
        try
        {
            String sqlCommand = "SELECT * FROM ? WHERE username = ? AND password = ?";
    
            // det er vores SQL sætning som vi beder om at få prepared til at blive sendt til databasen:
            PreparedStatement preparedStatement = lovestruckConnection.prepareStatement(sqlCommand);
    
            preparedStatement.setString(1, table);
            preparedStatement.setString(2, dataFromLogInForm.getParameter("usernameinput"));
            preparedStatement.setString(3, dataFromLogInForm.getParameter("passwordinput"));
            
            resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);
        }
        catch(SQLException e)
        {
            System.out.println("Error in findUserInDb: " + e.getMessage());
        }
        return resultSet;
    }
    
    
    
}
