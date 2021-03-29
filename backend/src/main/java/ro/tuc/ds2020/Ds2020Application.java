package ro.tuc.ds2020;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ro.tuc.ds2020.jsonrpc.MyJsonRpcConfig;

import java.util.TimeZone;

@SpringBootApplication
@Validated
public class Ds2020Application {
	@SneakyThrows
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(Ds2020Application.class, args);
		MyJsonRpcConfig.startServer();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowCredentials(true).allowedOrigins("*").allowedMethods("*");
			}
		};
	}
}
