package kiomnd2.cosmo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class CosmoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CosmoApiApplication.class, args);
    }

}
