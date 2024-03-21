package com.sopt.Server.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AgeEnumTest {
    private static final int eighteen = 18;
    private static final int twentyFive = 25;
    private static final int thirtyOne = 31;
    private static final int fortyThree = 43;
    private static final int fiftyFive = 55;

    @Test
    @DisplayName("18세는 10대이다")
    void getAgeEnumTeenager() {
        assertEquals(AgeEnum.TEENAGER, AgeEnum.getAgeEnum(eighteen));
    }

    @Test
    @DisplayName("25세는 20대이다")
    void getAgeEnumTwenties() {
        assertEquals(AgeEnum.TWENTIES, AgeEnum.getAgeEnum(twentyFive));
    }

    @Test
    @DisplayName("31세는 30대이다")
    void getAgeEnumThirties() {
        assertEquals(AgeEnum.THIRTIES, AgeEnum.getAgeEnum(thirtyOne));
    }

    @Test
    @DisplayName("43세는 40대이다")
    void getAgeEnumForties() {
        assertEquals(AgeEnum.FORTIES, AgeEnum.getAgeEnum(fortyThree));
    }

    @Test
    @DisplayName("55세는 50대이다")
    void getAgeEnumFifties() {
        assertEquals(AgeEnum.FIFTIES, AgeEnum.getAgeEnum(fiftyFive));
    }

}