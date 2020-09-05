package com.exemplo.livroapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@SpringBootApplication
public class LivroApiApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public Docket customImplementation() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.exemplo.livroapi"))
				.build().apiInfo(apiInfo()).tags(new Tag("tag1", "Documentação Livro API"));
	}
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("LIVRO API").version("01.00.00").build();
	}


	public static void main(String[] args) {
		SpringApplication.run(LivroApiApplication.class, args);
	}

}
