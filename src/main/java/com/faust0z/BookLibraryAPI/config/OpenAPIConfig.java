package com.faust0z.BookLibraryAPI;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        Info info = new Info()
                .title("Library Management API")
                .version("1.0")
                .description("""
                        <h2>A SpringBoot API to manage Users👥, Books📖, and Loans🗒️.</h2>
                        <br>
                        
                        <h2>🚀 User guide:</h3>
                        <ol>
                            <li><h3>Execute the <code>/auth/login</code> endpoint to receive a valid access token.</h3></li>
                            <li><h3>Copy the access token and paste it into the <code>Authorize 🔓</code> button (top right). The example user has admin access.</h3></li>
                        </ol>
                        <br>
                        <br>
                        <h3><a href='https://github.com/faust0z/librarymanagementapi' target='_blank'>Link to source code</a></h3>
                        """)
                .contact(new Contact().name("Faust0z").email("fausto.z77@hotmail.com"));

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme));
    }
}