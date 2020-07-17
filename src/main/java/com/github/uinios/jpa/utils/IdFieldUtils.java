package com.github.uinios.jpa.utils;

import com.github.uinios.jpa.exception.JpaServiceException;
import org.springframework.beans.BeanWrapperImpl;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

/**
 * reflection get id
 */
public class IdFieldUtils {

    private IdFieldUtils() {
    }

    public static <T> Optional<Object> getIdFieldValue(T entity, String keyFieldName) {
        BeanWrapperImpl wrapper = new BeanWrapperImpl(entity);
        return Optional.ofNullable(wrapper.getPropertyValue(keyFieldName));
    }

    public static Optional<String> getIdFieldName(Class<?> entity) {
        Optional<String> optional = findAnnotationIdFieldName(entity);
        if (optional.isPresent()) {
            return optional;
        } else {
            return findSuperclassIdFieldName(entity);
        }
    }

    private static Optional<String> findAnnotationIdFieldName(Class<?> entity) {
        String fieldName = null;
        Field[] fields = entity.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                fieldName = field.getName();
                break;
            }
        }
        return Optional.ofNullable(fieldName);
    }

    private static Optional<String> findSuperclassIdFieldName(Class<?> entity) {
        String fieldName = null;
        Class<?> superParent = entity.getSuperclass();
        String completePackage = superParent.getName();
        if (Objects.equals(completePackage, "java.lang.Object")) {
            String currentClassName = entity.getName();
            throw new JpaServiceException("Table " + currentClassName + " Not Found javax.persistence.Id");
        } else {
            Optional<String> optional = findAnnotationIdFieldName(superParent);
            if (optional.isPresent()) {
                fieldName = optional.get();
            } else {
                findSuperclassIdFieldName(superParent);
            }
        }
        return Optional.ofNullable(fieldName);
    }

}
