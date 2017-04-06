package hr.asc.appic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class WishoutApplication {

    public static void main(String[] args) {
        SpringApplication.run(WishoutApplication.class, args);
    }
}
