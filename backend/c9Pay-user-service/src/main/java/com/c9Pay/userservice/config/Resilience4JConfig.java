package com.c9Pay.userservice.config;

import com.c9Pay.userservice.web.exception.exceptions.InternalServerException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.util.Timeout;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

import static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType.COUNT_BASED;

@Slf4j
@Configuration
public class Resilience4JConfig {
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration(){
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(10) // CircuitBreaker 열리는 실패 임계값
                .waitDurationInOpenState(Duration.ofMillis(1000)) //1분안 동안 작동후 다시 닫힘
                .slidingWindowSize(2) //최근 호출 window 크기
                .recordExceptions(IOException.class, TimeoutException.class) // 기록할 exception
                .build();

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(3)) // 작업 최대 시간은 4초
                .build();

        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(config)
                .build());
    }
    public static <T> T circuitBreakerThrowable(String service){
        log.error("{} is Unavailable",service);
        throw new InternalServerException();
    }
}
