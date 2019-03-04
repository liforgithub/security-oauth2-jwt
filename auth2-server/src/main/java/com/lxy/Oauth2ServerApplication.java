package com.lxy;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableEurekaClient
@EntityScan("com.lxy")
public class Oauth2ServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Oauth2ServerApplication.class).web(true).run(args);
    }
}
