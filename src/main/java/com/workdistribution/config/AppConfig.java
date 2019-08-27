package com.workdistribution.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.workdistribution.utils.ServiceConstants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class AppConfig implements WebMvcConfigurer{

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

	@Bean
	public Docket dotAPI() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(wdsAPIInfo())
				.globalResponseMessage(RequestMethod.GET, globalResponseMessages())
				.globalResponseMessage(RequestMethod.POST, globalResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, globalResponseMessages()).select()
				.apis(RequestHandlerSelectors.basePackage("com.workdistribution")).paths(PathSelectors.any()).build();
	}

	private ApiInfo wdsAPIInfo() {
		return new ApiInfoBuilder().title("Work Distribution Service API Definitions")
				.description(
						"This is a complete API reference document for all Work Distribution Service")
				.version("v1").build();
	}
	
	private List<ResponseMessage> globalResponseMessages() {
		List<ResponseMessage> globalResponseMessages = new ArrayList<>();

		ResponseMessageBuilder httpOK = new ResponseMessageBuilder();
		httpOK.code(ServiceConstants.HTTPSTATUS_OK_CODE);
		httpOK.message(ServiceConstants.HTTPSTATUS_OK_VALUE);
		globalResponseMessages.add(httpOK.build());

		ResponseMessageBuilder httpBR = new ResponseMessageBuilder();
		httpBR.code(ServiceConstants.HTTPSTATUS_BADREQUEST_CODE);
		httpBR.message(ServiceConstants.HTTPSTATUS_BADREQUEST_VALUE);
		globalResponseMessages.add(httpBR.build());

		ResponseMessageBuilder httpNF = new ResponseMessageBuilder();
		httpNF.code(ServiceConstants.HTTPSTATUS_NOTFOUND_CODE);
		httpNF.message(ServiceConstants.HTTPSTATUS_NOTFOUND_VALUE);
		globalResponseMessages.add(httpNF.build());

		ResponseMessageBuilder httpSU = new ResponseMessageBuilder();
		httpSU.code(ServiceConstants.HTTPSTATUS_SERVICEUNAVAILABLE_CODE);
		httpSU.message(ServiceConstants.HTTPSTATUS_SERVICEUNAVAILABLE_VALUE);
		globalResponseMessages.add(httpSU.build());

		return globalResponseMessages;

	}

}
