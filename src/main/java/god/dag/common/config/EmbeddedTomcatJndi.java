package god.dag.common.config;

import com.zaxxer.hikari.HikariJNDIFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.Base64;

@Configuration
@Profile("local")
@Slf4j
public class EmbeddedTomcatJndi {

    @Value("${namdb.datasource.jdbc-url}")
    private String namJdbcUrl;

    @Value("${namdb.datasource.username}")
    private String namUsername;

    @Value("${namdb.datasource.password}")
    private String encryptedNamPassword;

    @Value("${namdb.datasource.driver-class-name}")
    private String namDriverClassName;


    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                tomcat.enableNaming();
                return new TomcatWebServer(tomcat, getPort() >= 0);
            }

            @Override
            protected void postProcessContext(Context context) {
                log.debug("Initializing tomcat factory JNDI...");

                Base64.Decoder decoder = Base64.getDecoder();
                //String namPassword = new String(decoder.decode(encryptedNamPassword));
                String namPassword = encryptedNamPassword; // no base64 for test

                // Adding the JNDI Details for IDS/NAMDB
                ContextResource namResource = new ContextResource();
                namResource.setName("jdbc/kcbNAM");
                namResource.setType(DataSource.class.getName());
                namResource.setProperty("driverClassName", namDriverClassName);
                namResource.setProperty("factory", HikariJNDIFactory.class.getName());
                namResource.setProperty("jdbcUrl", namJdbcUrl);
                namResource.setProperty("username", namUsername);
                namResource.setProperty("password", namPassword);
                namResource.setProperty("minimumIdle", "1");
                namResource.setProperty("maximumPoolSize", "1");
                context.getNamingResources().addResource(namResource);

            }
        };
    }
}
