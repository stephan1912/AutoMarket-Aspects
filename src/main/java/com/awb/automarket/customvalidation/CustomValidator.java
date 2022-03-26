package com.awb.automarket.customvalidation;

import com.awb.automarket.dto.ServiceResponseModel;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

public class CustomValidator {
    public static ServiceResponseModel ValidateObject(Object dto){
        try{
            CustomDtoClassAnnotation mappsEntity = dto.getClass().getAnnotation(CustomDtoClassAnnotation.class);
            if(mappsEntity == null) return null;

            Class<?> mappedClass = mappsEntity.mappsEntity();

            Field[] dtoFields = dto.getClass().getDeclaredFields();
            for (Field dtoField: dtoFields) {
                CustomDtoAnnotation dtoAnnotation = dtoField.getAnnotation(CustomDtoAnnotation.class);
                if(dtoAnnotation == null) continue;
                String field = dtoField.getName();
                Field mappedField = mappedClass.getDeclaredField(dtoAnnotation.mappsField());
                CustomEntityAnnotation entityAnnotation = mappedField.getAnnotation(CustomEntityAnnotation.class);
                if(entityAnnotation == null) continue;


                if(dtoField.getType() == String.class){
                    String dtoValue = (String) dtoField.get(dto);
                    if(entityAnnotation.required() == false && dtoValue.isEmpty()) continue;
                    if(dtoValue.length() < entityAnnotation.min() || dtoValue.length() > entityAnnotation.max()){
                        if(entityAnnotation.min() == 0){
                            return ServiceResponseModel.StringNotValid(entityAnnotation.fieldName(), entityAnnotation.max());
                        }
                        return ServiceResponseModel.StringNotValid(entityAnnotation.fieldName(), entityAnnotation.max(), entityAnnotation.min());
                    }
                }
                else if(dtoField.getType() == int.class || dtoField.getType() == Integer.class){
                    Integer dtoValue = (Integer) dtoField.get(dto);
                    if(entityAnnotation.required() == false && dtoValue == 0) continue;
                    if(dtoValue < entityAnnotation.min() || dtoValue > entityAnnotation.max()){
                        if(entityAnnotation.min() == 0){
                            return ServiceResponseModel.IntegerNotValid(entityAnnotation.fieldName(), entityAnnotation.max());
                        }
                        return ServiceResponseModel.IntegerNotValid(entityAnnotation.fieldName(), entityAnnotation.max(), entityAnnotation.min());
                    }
                }
                else if(dtoField.getType() == Date.class) {
                    Date dtoValue = (Date) dtoField.get(dto);
                    if(entityAnnotation.required() == false && dtoValue == null) continue;
                    Calendar min = Calendar.getInstance();
                    min.set(entityAnnotation.min(), 0, 1);
                    Calendar max = Calendar.getInstance();
                    max.set(entityAnnotation.max(), 0, 1);

                    if (dtoValue.before(min.getTime()) || dtoValue.after(max.getTime())) {
                        return ServiceResponseModel.InvalidDate(entityAnnotation.fieldName(), entityAnnotation.max(), entityAnnotation.min());
                    }
                }
            }
        }catch (NoSuchFieldException | IllegalAccessException ex){
            return null;
        }
        return null;
    }
}
