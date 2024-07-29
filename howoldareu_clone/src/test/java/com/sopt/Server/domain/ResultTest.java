package com.sopt.Server.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ResultTest {
    @Test
    @DisplayName("결과가 생성된 일자 정보를 반환한다")
    void getTestDateToString() {
        // given
        LocalDateTime testedDate = LocalDateTime.of(2023, 3, 21, 12, 30);
        Result result = Result.builder()
                .testedDate(testedDate)
                .build();
        String expected = "3월 21일";

        // when
        String resultString = result.getTestedDateToString();

        // then
        assertEquals(expected, resultString);
    }
}