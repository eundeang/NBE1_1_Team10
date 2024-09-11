package org.example.gc_coffee.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Validation test")
public class ValidationTest {

    @Nested
    @DisplayName("EmailGenerator validation test")
    class DescribeEmail {
        @Test
        @DisplayName("사용자가 기입한 이메일이 정규표현식에 맞을 경우")
        void ContextInvalidEmail() {
            String email = EmailGenerator.generateRandomValidEmail();
        }
    }
}
