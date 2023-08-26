package elethu.ikamva.utils;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Stream;

public class Validator {
    public static <T> boolean checkForNulls(T clazz) {
        Class<?> aClass = clazz.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        return Stream.of(declaredFields).allMatch(Objects::nonNull);
    }
}
