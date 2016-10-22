package spring.generic.server.Rests;

import org.springframework.web.bind.annotation.*;
import spring.generic.server.MongoDB.*;
import spring.generic.server.Utills.JSONUtills;
import spring.generic.server.Utills.Utills;

/**
 * Created by gadiel on 11/10/2016.
 */
@RestController
@RequestMapping("/user")
public class UserRest {

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUp(@RequestBody String userString) {
        SimpleUser user = new SimpleUser(userString);
        if (!DBUserUtills.isEmailExist(user.getEmail())) {
            DBUserUtills.insertUserByEntity(user);
            Utills.sendConfirmationEmail(user);
            return JSONUtills.getSuccessJSON();
        }
        return JSONUtills.getCustomizedReasonJSON("failed", "username already exist");
    }

    @RequestMapping(value = "/signupForAdmin", method = RequestMethod.POST)
    public String signupForAdmin(@RequestBody String userString) {
        AdminUser user = new AdminUser(userString);
        if (!DBUserUtills.isEmailExist(user.getEmail())) {
            DBUserUtills.insertUserByEntity(user);
            Utills.sendConfirmationEmail(user);
            return JSONUtills.getSuccessJSON();
        }
        return JSONUtills.getCustomizedReasonJSON("failed", "username already exist");
    }


    @RequestMapping(value = "activateAccount/{code}", method = RequestMethod.GET)
    String activateUser(@PathVariable String code) {
        if (DBUserUtills.activateUser(code)) {
            return JSONUtills.getSuccessJSON();
        }
        return JSONUtills.getFailedJSON();
    }

    @RequestMapping(value = "forgotPassword/{email:.+}", method = RequestMethod.GET)
    String forgotPassword(@PathVariable String email) {
        User user = DBUserUtills.changeActivationKey(email);
        if (user != null) {
            Utills.sendForgotPasswordEmail(user);
            return JSONUtills.getSuccessJSON();
        }
        return JSONUtills.getFailedJSON();
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePassword(@RequestBody String body) {
        if (DBUserUtills.changeUserPassword(body)) {
            return JSONUtills.getSuccessJSON();
        }
        return JSONUtills.getFailedJSON();
    }


}
