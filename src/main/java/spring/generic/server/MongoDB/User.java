package spring.generic.server.MongoDB;

/**
 * Created by gadiel on 12/10/2016.
 */

import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import spring.generic.server.Utills.Utills;

import java.text.SimpleDateFormat;
import java.util.Date;

@Document(collection = "users")
public abstract class User {
    @Id
    private String id;
    String password;
    String email;
    String activationKey;
    Date birthDate;
    boolean activated;

    public User() {
    }

    public User(String password, String email, Date birthDate) {
        this.password = new ShaPasswordEncoder().encodePassword(password, null);
        this.email = email;
        this.activationKey = Utills.createActivationKey();
        this.birthDate = birthDate;
        this.activated = false;
    }

    public User(String jsonUser) {
        this.activated = false;
        JSONObject jsonObject = new JSONObject(jsonUser);
        String cleanPassowrd = (String) jsonObject.get("password");
        this.password = new ShaPasswordEncoder().encodePassword(cleanPassowrd, null);
        this.email = (String) jsonObject.get("email");
        try {
            String dateValue = (String) jsonObject.get("birthdate");
            SimpleDateFormat df = new SimpleDateFormat("dd ,MM, yyyy");
            this.birthDate = df.parse(dateValue);
        } catch (Exception e) {
            this.birthDate = new Date();
        }
        this.activationKey = Utills.createActivationKey();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract String getRole();


}