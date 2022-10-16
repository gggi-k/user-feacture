package kr.project.userfeature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableTransactionManagement
@EnableJpaRepositories
@SpringBootApplication
public class UserFeatureApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserFeatureApplication.class, args);
    }

}