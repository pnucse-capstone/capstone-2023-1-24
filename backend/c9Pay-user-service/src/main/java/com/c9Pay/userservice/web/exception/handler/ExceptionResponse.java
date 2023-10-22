package com.c9Pay.userservice.web.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * 예외 응답 정보를 생성하는 클래스
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private String errorCode;

}
