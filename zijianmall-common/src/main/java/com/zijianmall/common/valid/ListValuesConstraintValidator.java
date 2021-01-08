package com.zijianmall.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xiaozj
 */
public class ListValuesConstraintValidator implements ConstraintValidator<ListValues, Integer> {

    private Set<Integer> set = new HashSet<>();

    @Override
    public void initialize(ListValues constraintAnnotation) {
        int[] values = constraintAnnotation.values();
        for (int val : values) {
            set.add(val);
        }

    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("dsd");
        return set.contains(integer);
    }
}
