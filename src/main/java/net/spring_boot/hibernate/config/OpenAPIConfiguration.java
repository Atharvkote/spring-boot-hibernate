package net.spring_boot.hibernate.config;

// OpenAPI
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

// Spring
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot Hibernate API")
                        .description("REST API demonstrating CRUD operations using Spring Boot, Spring Data JPA, Hibernate, and PostgreSQL.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Atharva Kote")
                                .url("https://github.com/Atharvkote")
                                .email("atharva@example.com")) // Replace with your email
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/Atharvkote/spring-boot-hibernate"));
    }
}