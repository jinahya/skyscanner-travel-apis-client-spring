package net.skyscanner.api.partners.apiservices;

import org.springframework.util.MultiValueMap;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static org.springframework.util.ReflectionUtils.doWithFields;

public final class FormDataEntries {

    public static <T> void toFormData(final Class<T> clazz, final T value, final MultiValueMap<String, String> map) {
        requireNonNull(clazz, "clazz is null");
        requireNonNull(value, "value is null");
        requireNonNull(map, "map is null");
        doWithFields(
                clazz,
                f -> {
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }
                    ofNullable(f.get(value))
                            .map(Object::toString)
                            .ifPresent(v -> map.add(f.getName(), v));
                },
                f -> f.isAnnotationPresent(FormDataEntry.class)
        );
    }

    private static <T> void toFormDataHelper(final Class<T> clazz, final Object value,
                                             final MultiValueMap<String, String> map) {
        requireNonNull(clazz, "clazz is null");
        toFormData(clazz, clazz.cast(value), map);
    }

    public static void toFormData(final Object value, final MultiValueMap<String, String> map) {
        requireNonNull(value, "value is null");
        toFormDataHelper(value.getClass(), value, map);
    }

    private FormDataEntries() {
        super();
    }
}
