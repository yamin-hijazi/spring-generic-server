package spring.generic.server.Rests;

import org.springframework.web.bind.annotation.*;
import spring.generic.server.MongoDB.DBUserUtills;
import spring.generic.server.Utills.JSONUtills;
import spring.generic.server.Utills.Utills;

/**
 * Created by gadiel on 11/10/2016.
 */
@RestController
@RequestMapping("/user")
public class User {

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUp(@RequestBody String userString) {
        spring.generic.server.MongoDB.User user = new spring.generic.server.MongoDB.User(userString);
        if (!DBUserUtills.isEmailExist(user.getEmail())) {
            DBUserUtills.insertUserByEntity(user);
            Utills.sendConfirmationEmail(user);
            return JSONUtills.getSuccessJSON();
        }
        return JSONUtills.getCustomizedReasonJSON("failed", "username already exist");
    }


    @RequestMapping(value = "activateAccount/{code}", method = RequestMethod.GET)
    String activateUser(@PathVariable String code) {
        //  spring.generic.server.Security.Others.NuvolaUserDetails user = (spring.generic.server.Security.Others.NuvolaUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //  String username = user.getUsername(); //get logged in username
        if (DBUserUtills.activateUser(code)) {
            return JSONUtills.getSuccessJSON();
        }
        return JSONUtills.getFailedJSON();
    }

    @RequestMapping(value = "forgotPassword/{email:.+}", method = RequestMethod.GET)
    String forgotPassword(@PathVariable String email) {
        spring.generic.server.MongoDB.User user = DBUserUtills.changeActivationKey(email);
        if (user != null) {
            Utills.sendConfirmationEmail(user);
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
