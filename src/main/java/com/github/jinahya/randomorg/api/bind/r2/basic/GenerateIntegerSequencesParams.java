package com.github.jinahya.randomorg.api.bind.r2.basic;

/*-
 * #%L
 * jsonrpc-bind-jackson
 * %%
 * Copyright (C) 2019 - 2020 Jinahya, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.github.jinahya.randomorg.api.bind.r2.AbstractParams;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

@Setter
@Getter
public class GenerateIntegerSequencesParams extends AbstractParams {

    public static final int MIN_N = GenerateIntegersParams.MIN_N;

    public static final int MAX_N = 1000;

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + "{"
               + "n=" + getN()
               + ",length=" + getLength()
               + ",min=" + getMin()
               + ",max=" + getMax()
               + ",replacement=" + getReplacement()
               + ",base=" + getBase()
               + "}";
    }

    // --------------------------------------------------------------------------------------------------------------- n
    public int getN() {
        if (units == null || units.isEmpty()) {
            throw new IllegalStateException("units are empty");
        }
        if (units.size() == 1) {
            return n;
        }
        return units.size();
    }

    public void setN(final int n) {
        this.n = n;
    }

    // ---------------------------------------------------------------------------------------------------------- length
    public Object getLength() {
        if (units == null || units.isEmpty()) {
            throw new IllegalStateException("units are empty");
        }
        if (units.size() == 1) {
            return units.get(0).getLength();
        }
        return units.stream().map(GenerateIntegerSequencesParamsUnit::getLength).collect(toList());
    }

    @SuppressWarnings({"unchecked"})
    public void setLength(final Object length) {
        requireNonNull(length, "length is null");
        if (length instanceof Number) {
            getUnitAt(((Number) length).intValue() - 1);
            return;
        }
        if (length instanceof Iterable) {
            int index = 0;
            for (final Integer l : (Iterable<Integer>) length) {
                getUnitAt(index++).setLength(l);
            }
            return;
        }
        throw new IllegalArgumentException("illegal length: " + length);
    }

    // ------------------------------------------------------------------------------------------------------------- min
    public Object getMin() {
        if (units == null || units.isEmpty()) {
            throw new IllegalStateException("units are empty");
        }
        if (units.size() == 1) {
            return units.get(0).getMin();
        }
        return units.stream().map(GenerateIntegerSequencesParamsUnit::getMin).collect(toList());
    }

    @SuppressWarnings({"unchecked"})
    public void setMin(final Object min) {
        requireNonNull(min, "min is null");
        if (min instanceof Number) {
            getUnitAt(((Number) min).intValue() - 1);
            return;
        }
        if (min instanceof Iterable) {
            int index = 0;
            for (final Integer e : (Iterable<Integer>) min) {
                getUnitAt(index++).setMin(e);
            }
            return;
        }
        throw new IllegalArgumentException("illegal min: " + min);
    }

    // ------------------------------------------------------------------------------------------------------------- max
    public Object getMax() {
        if (units == null || units.isEmpty()) {
            throw new IllegalStateException("units are empty");
        }
        if (units.size() == 1) {
            return units.get(0).getMax();
        }
        return units.stream().map(GenerateIntegerSequencesParamsUnit::getMax).collect(toList());
    }

    @SuppressWarnings({"unchecked"})
    public void setMax(final Object max) {
        requireNonNull(max, "max is null");
        if (max instanceof Number) {
            getUnitAt(((Number) max).intValue() - 1);
            return;
        }
        if (max instanceof Iterable) {
            int index = 0;
            for (final Integer e : (Iterable<Integer>) max) {
                getUnitAt(index++).setMax(e);
            }
            return;
        }
        throw new IllegalArgumentException("illegal max: " + max);
    }

    // ----------------------------------------------------------------------------------------------------- replacement
    public Object getReplacement() {
        if (units == null || units.isEmpty()) {
            throw new IllegalStateException("units are empty");
        }
        if (units.size() == 1) {
            return units.get(0).getReplacement();
        }
        return units.stream().map(GenerateIntegerSequencesParamsUnit::getReplacement).collect(toList());
    }

    @SuppressWarnings({"unchecked"})
    public void setReplacement(final Object replacement) {
        requireNonNull(replacement, "replacement is null");
        if (replacement instanceof Number) {
            getUnitAt(((Number) replacement).intValue() - 1);
            return;
        }
        if (replacement instanceof Iterable) {
            int index = 0;
            for (final Boolean e : (Iterable<Boolean>) replacement) {
                getUnitAt(index++).setReplacement(e);
            }
            return;
        }
        throw new IllegalArgumentException("illegal replacement: " + replacement);
    }

    // ------------------------------------------------------------------------------------------------------------ base
    public Object getBase() {
        if (units == null || units.isEmpty()) {
            throw new IllegalStateException("units are empty");
        }
        if (units.size() == 1) {
            return units.get(0).getBase();
        }
        return units.stream().map(GenerateIntegerSequencesParamsUnit::getBase).collect(toList());
    }

    @SuppressWarnings({"unchecked"})
    public void setBase(final Object base) {
        requireNonNull(base, "base is null");
        if (base instanceof Number) {
            getUnitAt(((Number) base).intValue() - 1);
            return;
        }
        if (base instanceof Iterable) {
            int index = 0;
            for (final Integer e : (Iterable<Integer>) base) {
                getUnitAt(index++).setBase(e);
            }
            return;
        }
        throw new IllegalArgumentException("illegal base: " + base);
    }

    // ----------------------------------------------------------------------------------------------------------- units
    public GenerateIntegerSequencesParams addUnit(final GenerateIntegerSequencesParamsUnit unit) {
        requireNonNull(unit, "unit is null");
        getUnits().add(unit);
        return this;
    }

    private GenerateIntegerSequencesParamsUnit getUnitAt(final int index) {
        while (getUnits().size() <= index) {
            addUnit(new GenerateIntegerSequencesParamsUnit());
        }
        return getUnits().get(index);
    }

    private List<GenerateIntegerSequencesParamsUnit> getUnits() {
        if (units == null) {
            units = new ArrayList<>();
        }
        return units;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Max(MAX_N)
    @Min(MIN_N)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private int n = MIN_N;

    @Size(min = MIN_N, max = MAX_N)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private List<@Valid @NotNull GenerateIntegerSequencesParamsUnit> units;
}
