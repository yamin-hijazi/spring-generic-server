package spring.generic.server.MongoDB;

/**
 * Created by gadiel on 12/10/2016.
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration {

    @Override
    public String getDatabaseName() {
        return "mongodb";
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient("54.149.15.156");
    }
}