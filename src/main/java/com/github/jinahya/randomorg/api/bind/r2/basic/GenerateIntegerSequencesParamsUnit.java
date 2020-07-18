package com.github.jinahya.randomorg.api.bind.r2.basic;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class GenerateIntegerSequencesParamsUnit extends GenerateIntegersParamsUnit {

    public static final int MIN_LENGTH = 1;

    public static final int MAX_LENGTH = 10000;

    // -----------------------------------------------------------------------------------------------------------------
    public int getLength() {
        return length;
    }

    public void setLength(final int length) {
        this.length = length;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Max(MAX_LENGTH)
    @Min(MIN_LENGTH)
    private int length;
}
