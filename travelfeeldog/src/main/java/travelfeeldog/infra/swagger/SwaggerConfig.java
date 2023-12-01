package travelfeeldog.infra.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("https://api.sunghyun98.com").description("Test server"))
                .addServersItem(new Server().url("http://localhost:8080").description("Local server"))
                .info(new Info().title("API").description("API Description").version("1.0.0"));
    }
}
