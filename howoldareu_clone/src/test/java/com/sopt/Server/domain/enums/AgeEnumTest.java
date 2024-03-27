package com.sopt.Server.domain.enums;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AgeEnumTest {
    private static final int NINETEEN = 19;
    private static final int TWENTY_ONE = 21;
    private static final int TWENTY_NINE = 29;
    private static final int THIRTY_ONE = 31;
    private static final int THIRTY_NINE = 39;
    private static final int FORTY_ONE = 41;
    private static final int FORTY_NINE = 49;
    private static final int FIFTY_ONE = 51;


    @Test
    @DisplayName("연령대로 원하는 정보를 가져올 수 있다.")
    void getAgeEnum() {
        // then
        Assertions.assertThat(AgeEnum.getAgeEnum(NINETEEN)).isEqualTo(AgeEnum.TEENAGER);
        Assertions.assertThat(AgeEnum.getAgeEnum(TWENTY_ONE)).isEqualTo(AgeEnum.TWENTIES);
        Assertions.assertThat(AgeEnum.getAgeEnum(TWENTY_NINE)).isEqualTo(AgeEnum.TWENTIES);
        Assertions.assertThat(AgeEnum.getAgeEnum(THIRTY_ONE)).isEqualTo(AgeEnum.THIRTIES);
        Assertions.assertThat(AgeEnum.getAgeEnum(THIRTY_NINE)).isEqualTo(AgeEnum.THIRTIES);
        Assertions.assertThat(AgeEnum.getAgeEnum(FORTY_ONE)).isEqualTo(AgeEnum.FORTIES);
        Assertions.assertThat(AgeEnum.getAgeEnum(FORTY_NINE)).isEqualTo(AgeEnum.FORTIES);
        Assertions.assertThat(AgeEnum.getAgeEnum(FIFTY_ONE)).isEqualTo(AgeEnum.FIFTIES);
    }

}