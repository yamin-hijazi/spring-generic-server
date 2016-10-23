package spring.generic.server.Security;

/**
 * Created by gadiel on 23/10/2016.
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {



    @Bean
    public CORSFilter corsFilter() {
        return new CORSFilter();
    }

}