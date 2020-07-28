package net.skyscanner.api.partners.apiservices;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.beans.Transient;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Slf4j
public abstract class AbstractObject implements Serializable {

    /**
     * Sets an entry of unrecognized property.
     *
     * @param key   the key of the unrecognized property.
     * @param value the value of the unrecognized property.
     * @return previous value mapped to the {@code key}.
     */
    @JsonAnySetter
    public Object putUnrecognizedProperty(final String key, final Object value) {
        requireNonNull(key, "key is null");
        return getUnrecognizedProperties().put(key, value);
    }

    /**
     * Returns unrecognized properties of this object.
     *
     * @return unrecognized properties of this object.
     */
    @JsonAnyGetter
    @Transient
    public Map<String, Object> getUnrecognizedProperties() {
        if (unrecognizedProperties == null) {
            unrecognizedProperties = new HashMap<>();
        }
        return unrecognizedProperties;
    }

    private Map<String, Object> unrecognizedProperties;
}
