package com.softuni.StudentClubs.validation;

import java.lang.annotation.Retention;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@javax.validation.Constraint(validatedBy = FieldMatchValidator.class)
@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
public @interface FieldMatch {
    String first();

    String second();

    String message() default "The fields must match";

    java.lang.Class<?>[] groups() default {};

    java.lang.Class<? extends javax.validation.Payload>[] payload() default {};
}
