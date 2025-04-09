package com.example.petstore.config;

import java.util.*;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .tags(apiTags())
                .components(apiComponents())
                .externalDocs(externalDocs());
    }

    private Info apiInfo() {
        return new Info()
                .title("Petstore API")
                .description("Comprehensive documentation for the Petstore API.")
                .version("1.0")
                .termsOfService("https://www.petstore.com/terms")
                .contact(new Contact()
                        .name("Petstore Support")
                        .url("https://www.petstore.com/contact")
                        .email("support@petstore.com")
                );
    }

    private List<Tag> apiTags() {
        return Arrays.asList(
                new Tag()
                        .name("Pets")
                        .description("Operations related to pets, including adding, removing, and updating pet details."),
                new Tag()
                        .name("Users")
                        .description("Operations for user registration, authentication, and managing user profiles.")
        );
    }

    private Components apiComponents() {
        return new Components()
                .addResponses("200", apiResponse("Successful operation"))
                .addResponses("400", apiResponse("Bad Request"))
                .addResponses("404", apiResponse("Resource not found"))
                .addResponses("500", apiResponse("Internal Server Error"))
                .addParameters("X-Request-ID", requestIDParameter())
                .addSchemas("Pet", petSchema());
    }

    private ApiResponse apiResponse(String description) {
        return new ApiResponse().description(description);
    }

    private Parameter requestIDParameter() {
        return new Parameter()
                .in("header")
                .name("X-Request-ID")
                .description("Unique request identifier for tracking purposes.")
                .required(true)
                .schema(new StringSchema());
    }

    private Schema<?> petSchema() {
        return new Schema<>()
                .type("object")
                .properties(Map.of(
                        "id", new IntegerSchema(),
                        "name", new StringSchema(),
                        "status", new StringSchema()._enum(Arrays.asList("available", "sold", "adopted"))
                ));
    }

    private ExternalDocumentation externalDocs() {
        return new ExternalDocumentation()
                .description("Find out more about the Petstore API")
                .url("https://www.petstore.com/docs");
    }
}