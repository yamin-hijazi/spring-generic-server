package spring.generic.server.Security.Others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.generic.server.MongoDB.DBUserUtills;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final static String USER_TEST = "root";

    private final LoggedInChecker loggedInChecker;

    @Autowired
    UserServiceImpl(LoggedInChecker loggedInChecker) {
        this.loggedInChecker = loggedInChecker;
    }

    @Override
    public User getUserByUsername(String email) {
        User securityUser = new User(); // this is the security User
        spring.generic.server.MongoDB.User DBUser = DBUserUtills.getUserByEmail(email);     //  this is the DB User
        if (DBUser != null) {   // if the user exist in the DB
            securityUser.setLogin(email);   // set the security User "true login (username)" as the login from DB
            securityUser.setPassword(DBUser.getPassword()); // set the security User "true password" as the login from DB
            return securityUser;    // return the security User. in the background Spring mechanism will compare the TRUE values against the ones received from the UI
        } else {
            return null;
        }
        //since it so unclear, the process goes this way:
        // a. calling getUserByUsername method (current).
        // b. get the User object from the db according to the email
        // c. set username and password in the security User Object - those are the real values
        // d. spring compare the true values against the ones received in the UI (those parameters are not shown here
    }

    @Override
    public List<String> getPermissions(String username) {
        List<String> permissions = new ArrayList<>();
        permissions.add("ROLE_ADMIN");
        return permissions;
    }

    @Override
    public User getCurrentUser() {
        return loggedInChecker.getLoggedInUser();
    }

    @Override
    public Boolean isCurrentUserLoggedIn() {
        return loggedInChecker.getLoggedInUser() != null;
    }
}
