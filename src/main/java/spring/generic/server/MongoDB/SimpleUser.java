package spring.generic.server.MongoDB;

import spring.generic.server.Security.Parameters.UserRoles;

import java.util.Date;

/**
 * Created by gadiel on 22/10/2016.
 */
public class SimpleUser extends User{

    public SimpleUser(String password, String email, Date birthDate) {
        super(password,email,birthDate);
    }

    public SimpleUser(String jsonUser) {
        super(jsonUser);
    }

    public SimpleUser() {
        super();
    }

    public String getRole(){
        return UserRoles.SimpleUser;
    }
}
