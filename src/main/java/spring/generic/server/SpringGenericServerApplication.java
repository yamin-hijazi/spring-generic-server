package spring.generic.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class SpringGenericServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringGenericServerApplication.class, args);
	}
}
