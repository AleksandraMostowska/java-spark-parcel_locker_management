package mostowska.aleksandra.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mostowska.aleksandra.config.adapter.LocalDateTimeAdapter;
import org.jdbi.v3.core.Jdbi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * Spring configuration class that sets up the application's beans and component scanning.
 *
 * This class is responsible for configuring the Jdbi instance for database access
 * and the Gson instance for JSON serialization/deserialization. It also specifies
 * the base package for component scanning.
 */
@Configuration
@ComponentScan("mostowska.aleksandra")
public class AppConfig {

    /**
     * Creates and configures a Jdbi bean for database access.
     *
     * The Jdbi instance is configured to connect to a MySQL database with
     * the specified URL, username, and password. The connection URL includes options
     * for timezone and Unicode support.
     *
     * @return A configured Jdbi instance.
     */
    @Bean
    public Jdbi jdbi() {
        var URL = "jdbc:mysql://localhost:3307/db_1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        var USERNAME = "user";
        var PASSWORD = "user1234";
        return Jdbi.create(URL, USERNAME, PASSWORD);
    }

    /**
     * Creates and configures a Gson bean for JSON serialization/deserialization.
     *
     * The Gson instance is configured with pretty-printing enabled and
     * a custom LocalDateTime adapter to handle serialization and deserialization
     * of LocalDateTime objects.
     *
     * @return A configured Gson instance.
     */
    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }
}
