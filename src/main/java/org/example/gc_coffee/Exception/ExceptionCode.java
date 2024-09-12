package org.example.gc_coffee.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    //Bad Request
    NOT_FOUND_PRODUCT_ID(400, "해당하는 상품이 존재하지 않습니다."),
    NOT_FOUND_ORDER_ID(400, "취소되었거나 존재하지 않는 주문입니다."),
    NOT_FOUND_ITEM_ID(400, "해당 주문에 상품이 존재하지 않습니다."),

    //Unauthorized
    INVALID_PASSWORD(401, "이메일이나 비밀번호가 일치하지 않습니다."),
    INVALID_EMAIL(401, "이메일이나 비밀번호가 일치하지 않습니다."),

    //conflict
    DUPLICATED_PRODUCT_NAME(409, "이미 존재하는 상품명입니다."),


    // Internal Server Error
    INTERNAL_SERVER_ERROR(500, "서버에서 문제가 발생했습니다."),
    DATABASE_CONNECTION_ERROR(500, "데이터베이스 연결에 실패했습니다."),
    // FILE_PROCESSING_ERROR(500, "파일 처리 중 오류가 발생했습니다."),
    // EXTERNAL_API_ERROR(502, "외부 API 호출 중 오류가 발생했습니다."),
    SERVICE_UNAVAILABLE(503, "현재 서비스를 사용할 수 없습니다."),
    TIMEOUT_ERROR(504, "서버 응답 시간이 초과되었습니다.");

    private final int code;
    private final String message;
}
