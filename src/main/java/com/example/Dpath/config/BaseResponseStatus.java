package com.example.Dpath.config;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청성공

     */
    SUCCESS(true,1000,"요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    //Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요"),
    EMPTY_FESTIVAL_NAME(false,2001,"축제 이름을 입력해주세요"),
    EMPTY_UNIVERSITY_NAME(false,2002,"대학교 이름을 입력해주세요"),
    EMPTY_LOCATION_NAME(false,2003,"대학교 주소를 입력해주세요"),
    EMPTY_DATE(false,2004,"행사 일자를 입력해주세요"),



    /**
     * 3000 : Response 오류
     */


    /**
     * 4000 : Database, Server 오류
     */
    NOT_EXIST_UNIVERSITY(false, 4010, "등록되지 않은 대학교 입니다."),
    NOT_EXIST_CELEBRITY(false, 4011, "등록되지 않은 연예인 입니다."),
    DATABASE_ERROR(false,4012,"데이터 베이스 오류입니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
