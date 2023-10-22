package com.c9Pay.userservice.config;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 컨트롤러 경로 설정을 구성하는 클래스
 */
@Component
public class WebConfig implements WebMvcConfigurer {
    /**
     * 경로 매칭 설정을 구성한다.
     * "/user-service"로 시작하는 경로에 대해서만 @RestController 어노테이션이 적용된 컨트롤러에 매핑된다.
     *
     * @param configurer 경로 매칭 구성을 위한 PathMatchConfigurer 객체
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/user-service", HandlerTypePredicate.forAnnotation(RestController.class));
    }
}
