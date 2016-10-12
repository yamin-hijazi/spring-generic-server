package spring.generic.server.Security.Others;

import java.util.List;

public interface UserService {
    User getUserByUsername(String username);

    List<String> getPermissions(String username);

    User getCurrentUser();

    Boolean isCurrentUserLoggedIn();
}
