package br.com.castgroup.desafio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.castgroup.desafio"))
				.paths(regex("/rest.*"))
				.build()
				.apiInfo(metaInfo());
	}
	
	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo(
				"API para crud de Pessoas",
				"Esta api tem como objeto:\n"
				+ "* cadastrar pessoas,\n"
				+ "* listar todas as pessoas,\n" 
				+ "* buscar e remover pessoas pelo id",
				"Versao 1.0","githrb.com/cassianoricardo",
				  new Contact("Cassiano Ricardo", "github.com/cassianoricardo", "cassiano_ricardo@hotmail.com"),
				  "",
	              "",
	              Collections.emptyList());
		return apiInfo;
	}
}
