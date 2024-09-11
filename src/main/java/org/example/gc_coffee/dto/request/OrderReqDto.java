package org.example.gc_coffee.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderReqDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+[.][a-zA-Z]{2,3}$",
            message="이메일 주소를 확인해주세요")
    private String email;

    @NotBlank(message = "주소 정보를 입력해주세요.")
    private String address;

    @NotBlank(message = "우편번호 정보를 입력해주세요.")
    private String postcode;

    @NotNull(message = "주문 상품이 없습니다.")
    @Valid
    private List<OrderProductReqDto> orderProductList;

}
