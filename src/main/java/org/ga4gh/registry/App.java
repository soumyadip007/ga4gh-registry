/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.ga4gh.registry;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@SpringBootApplication
@Configuration
public class App {
    
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {

        OpenAPI openAPI = new OpenAPI();
        openAPI.info(
            new Info()
                .title("GA4GH Standards and Implementations Registry")
                .version("1.0.0")
                .description("Browse GA4GH standards and associated implementations")
                .contact(new Contact()
                    .name("GA4GH Dev Team")
                    .email("jeremy.adams@ga4gh.org")
                    .url("https://ga4gh.org")
                )
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                )
        );
        List<Server> servers = new ArrayList<>();
        servers.add(
            new Server()
                .url("https://registry.ga4gh.org/v1")
                .description("Production API")
        );
        openAPI.servers(servers);
        return openAPI;
    }

    @Bean
    public Jackson2ObjectMapperBuilder configureObjectMapper() {
        Jackson2ObjectMapperBuilder builder =  new Jackson2ObjectMapperBuilder();
        builder.modulesToInstall(Hibernate4Module.class);
        // builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        return builder;
    }
}
