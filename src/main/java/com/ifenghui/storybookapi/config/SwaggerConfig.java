package com.ifenghui.storybookapi.config;

/**
 * Created by wslhk on 2016/12/20.
 */

        import static springfox.documentation.builders.PathSelectors.regex;

        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.web.context.request.async.DeferredResult;

        import springfox.documentation.builders.ApiInfoBuilder;
        import springfox.documentation.builders.ParameterBuilder;
        import springfox.documentation.builders.PathSelectors;
        import springfox.documentation.builders.RequestHandlerSelectors;
        import springfox.documentation.schema.ModelRef;
        import springfox.documentation.service.ApiInfo;
        import springfox.documentation.service.Contact;
        import springfox.documentation.service.Parameter;
        import springfox.documentation.spi.DocumentationType;
        import springfox.documentation.spring.web.plugins.Docket;
        import springfox.documentation.swagger2.annotations.EnableSwagger2;

        import java.util.ArrayList;
        import java.util.List;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * SpringBoot默认已经将classpath:/META-INF/resources/和classpath:/META-INF/resources/webjars/映射
     * 所以该方法不需要重写，如果在SpringMVC中，可能需要重写定义（我没有尝试）
     * 重写该方法需要 extends WebMvcConfigurerAdapter
     *
     */

    /**
     * 可以定义多个组，比如本类中定义把test和demo区分开了
     * （访问页面就可以看到效果了）
     *
     */
    public List<Parameter> globeParams() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameterBuilder.name("ssToken")
                .description("ssToken")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build();
        parameters.add(parameterBuilder.build());
        return parameters;
    }

    public List<Parameter> globeAdminParams() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameterBuilder.name("manager-token")
                .description("manager-token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build();
        parameters.add(parameterBuilder.build());
        return parameters;
    }

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("api")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ifenghui.storybookapi.app"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(this.globeParams());
    }

    @Bean
    public Docket swaggerSpringMvcPluginAdmin() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("admin")
                .apiInfo(apiInfoAdmin())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ifenghui.storybookapi.adminapi"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(this.globeAdminParams());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Simple APIs")
                .description("simple apis")
                .termsOfServiceUrl("http://www.gm.com")
                .contact(new Contact("suliyea", "http://xxx", "suliyea@qq.com"))
                .version("1.0")
                .build();
    }
    private ApiInfo apiInfoAdmin() {
        return new ApiInfoBuilder().title("Simple APIs")
                .description("admin apis")
                .termsOfServiceUrl("http://www.gm.com")
                .contact(new Contact("suliyea", "http://xxx", "suliyea@qq.com"))
                .version("1.0")
                .build();
    }

    @Bean
    public Docket swaggerActivePlugin() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("activity1902")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ifenghui.storybookapi.active1902"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(this.globeParams());
    }


    @Bean
    public Docket swaggerSale() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("presale")
                .apiInfo(apiInfoAdmin())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ifenghui.storybookapi.app.presale.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(this.globeAdminParams());
    }
    @Bean
    public Docket swaggerSaleAdmin() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("presaleadmin")
                .apiInfo(apiInfoAdmin())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ifenghui.storybookapi.app.presale.admin"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(this.globeAdminParams());
    }
}