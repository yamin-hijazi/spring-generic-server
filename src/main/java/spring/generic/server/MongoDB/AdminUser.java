package spring.generic.server.MongoDB;

import spring.generic.server.Security.Parameters.UserRoles;

import java.util.Date;

/**
 * Created by gadiel on 22/10/2016.
 */
public class AdminUser extends User{
    public AdminUser(String password, String email, Date birthDate) {
       super(password,email,birthDate);
    }

    public AdminUser(String jsonUser) {
        super(jsonUser);
    }

    public AdminUser() {
        super();
    }

    public String getRole(){
        return UserRoles.AdminUser;
    }
}
