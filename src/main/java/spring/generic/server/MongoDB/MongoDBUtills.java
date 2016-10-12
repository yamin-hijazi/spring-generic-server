package spring.generic.server.MongoDB;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by gadiel on 12/10/2016.
 */
public class MongoDBUtills {

public static boolean isVlidate(String username, String password){
    ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
    Query searchUserQuery = new Query(Criteria.where("username").is(username));
    User savedUser = mongoOperation.findOne(searchUserQuery, User.class);
    if(savedUser!= null && savedUser.getPassword().equals(password)){
        return true;
    }
    return false;
}


    public static User getUserByUsername(String username) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
        Query searchUserQuery = new Query(Criteria.where("username").is(username));
        User savedUser = mongoOperation.findOne(searchUserQuery, User.class);
        if (savedUser != null ) {
            return savedUser;
        }
        return null;
    }

    public static void insertUser(String username, String password){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
        User user = new User(username, password);
        mongoOperation.save(user);
    }

}
