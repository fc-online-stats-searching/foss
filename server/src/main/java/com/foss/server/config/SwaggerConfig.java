package com.foss.server.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@OpenAPIDefinition(
        info = @Info(title = "FOSS App",
                description = "FOSS app api명세",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        io.swagger.v3.oas.models.info.Info info = new io.swagger.v3.oas.models.info.Info()
                .title("API")
                .version("v1")
                .description("잘못된 부분이나 오류 발생 시 바로 말씀해주세요.");

        Server localServer = new Server();
        localServer.setDescription("HTTP");
        localServer.setUrl("http://localhost:8080");

        Server server = new Server();
        server.setDescription("HTTP");
        server.setUrl("http://203.252.161.105:8080");

        return new OpenAPI()
                .info(info)
                .components(new Components())
                .servers(Arrays.asList(server, localServer));

    }
}
