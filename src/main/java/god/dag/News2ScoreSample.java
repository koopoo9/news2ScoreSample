package god.dag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = { "kcb" })
public class News2ScoreSample extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(News2ScoreSample.class, args);
    }
}