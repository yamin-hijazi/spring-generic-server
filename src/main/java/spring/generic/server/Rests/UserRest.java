package spring.generic.server.Rests;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.generic.server.MongoDB.User.AdminUser;
import spring.generic.server.MongoDB.User.SimpleUser;
import spring.generic.server.MongoDB.User.User;
import spring.generic.server.MongoDB.User.UserUtills;
import spring.generic.server.Utills.EmailUtills;
import spring.generic.server.Utills.JSONUtills;

/**
 * Created by gadiel on 11/10/2016.
 */
@RestController
@RequestMapping("/user")
public class UserRest {

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUp(@RequestBody String userString) {
        SimpleUser user = new SimpleUser(userString);
        if (!UserUtills.isEmailExist(user.getEmail())) {
            UserUtills.insertUserByEntity(user);
            EmailUtills.sendConfirmationEmail(user);
            return JSONUtills.getSuccessJSON();
        }
        return JSONUtills.getCustomizedReasonJSON(false, "username already exist");
    }

    @RequestMapping(value = "/signupForAdmin", method = RequestMethod.POST)
    public String signupForAdmin(@RequestBody String userString) {
        AdminUser user = new AdminUser(userString);
        if (!UserUtills.isEmailExist(user.getEmail())) {
            UserUtills.insertUserByEntity(user);
            EmailUtills.sendConfirmationEmail(user);
            return JSONUtills.getSuccessJSON();
        }
        return JSONUtills.getCustomizedReasonJSON(false, "username already exist");
    }

    @RequestMapping(value = "activateAccount/{code}", method = RequestMethod.GET)
    String activateUser(@PathVariable String code) {
        if (UserUtills.activateUser(code)) {
            return JSONUtills.getSuccessJSON();
        }
        return JSONUtills.getFailedJSON();
    }

    @RequestMapping(value = "forgotPassword/{email:.+}", method = RequestMethod.GET)
    String forgotPassword(@PathVariable String email) {
        User user = UserUtills.changeActivationKey(email);
        if (user != null) {
            EmailUtills.sendForgotPasswordEmail(user);
            return JSONUtills.getSuccessJSON();
        }
        return JSONUtills.getFailedJSON();
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePassword(@RequestBody String body) {
        if (UserUtills.changeUserPassword(body)) {
            return JSONUtills.getSuccessJSON();
        }
        return JSONUtills.getFailedJSON();
    }

    @RequestMapping(value = "/isloggedin", method = RequestMethod.GET)
    public String isloggedin() {
        try {
            spring.generic.server.Security.Others.NuvolaUserDetails user = (spring.generic.server.Security.Others.NuvolaUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                return JSONUtills.getCustomizedReasonJSON(true, user.getUsername());
            }
            return JSONUtills.getFailedJSON();
        }
        catch (Exception e){
           return  JSONUtills.getFailedJSON();
        }
    }

}
