package spring.generic.server.MongoDB;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import spring.generic.server.Utills.Utills;

/**
 * Created by gadiel on 12/10/2016.
 */
public class DBUserUtills {

    public static User getUserByEmail(String email) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
        Query searchUserQuery = new Query(Criteria.where("email").is(email));
        User savedUser = mongoOperation.findOne(searchUserQuery, User.class);
        if (savedUser != null ) {
            return savedUser;
        }
        return null;
    }

    public static boolean isEmailExist(String email) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
        Query searchUserQuery = new Query(Criteria.where("email").is(email));
        User savedUser = mongoOperation.findOne(searchUserQuery, User.class);
        if (savedUser != null ) {
            return true;
        }
        return false;
    }

    public static void insertUserByEntity(User user){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
        mongoOperation.save(user);
    }

    public static boolean activateUser(String code){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
        Query searchUserQuery = new Query(Criteria.where("activationKey").is(code));
        User savedUser = mongoOperation.findOne(searchUserQuery, User.class);
        if (savedUser != null && !savedUser.activated) {
            savedUser.activated = true;
            savedUser.setActivationKey(Utills.createActivationKey());
            mongoOperation.save(savedUser);
            return true;
        }
        return false;
    }

    public static User changeActivationKey(String email){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
        Query searchUserQuery = new Query(Criteria.where("email").is(email));
        User savedUser = mongoOperation.findOne(searchUserQuery, User.class);
        if (savedUser != null) {
            savedUser.setActivationKey(Utills.createActivationKey());
            mongoOperation.save(savedUser);
            return savedUser;
        }
        return null;
    }

    public static boolean changeUserPassword(String body){
        String code="";
        String password="";
        try {
            JSONObject jsonObject = new JSONObject(body);
            code = (String)jsonObject.get("code");
            password = (String)jsonObject.get("password");
        }
        catch (JSONException e){
            return false;
        }
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
        Query searchUserQuery = new Query(Criteria.where("activationKey").is(code));
        User savedUser = mongoOperation.findOne(searchUserQuery, User.class);
        if (savedUser != null) {
            savedUser.setActivationKey(Utills.createActivationKey());
            savedUser.setPassword(password);
            mongoOperation.save(savedUser);
            return true;
        }
        return false;
    }

    //public static boolean isVlidate(String username, String password){
//    ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
//    MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
//    Query searchUserQuery = new Query(Criteria.where("username").is(username));
//    User savedUser = mongoOperation.findOne(searchUserQuery, User.class);
//    if(savedUser!= null && savedUser.getPassword().equals(password)){
//        return true;
//    }
//    return false;
//}

//    public static void insertUserByStrings(String username, String password, String email, Date birthday){
//        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
//        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
//        User user = new User(password,email,birthday);
//        mongoOperation.save(user);
//    }
}
