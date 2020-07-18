package com.github.jinahya.randomorg.api.bind.r2;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

@Setter
@Getter
public abstract class AbstractGenerateIntegersResultRandom {

    // -------------------------------------------------------------------------------------------------- completionTime
    public TemporalAccessor getCompletionTimeParsedWith(final DateTimeFormatter formatter) {
        requireNonNull(formatter, "formatter is null");
        return ofNullable(getCompletionTime()).map(formatter::parse).orElse(null);
    }

    public Instant getCompletionTimeAsInstant() {
        return ofNullable(getCompletionTimeParsedWith(DateTimeFormatter.ISO_INSTANT)).map(Instant::from).orElse(null);
    }

    // -------------------------------------------------------------------------------------------------------------
    @NotEmpty
    private List<Integer> data;

    @NotBlank
    private String completionTime;
}
