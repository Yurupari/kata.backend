package com.haiilo.kata.backend.model.converter;

import com.haiilo.kata.backend.model.enums.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {
    @Override
    public String convertToDatabaseColumn(Status status) {
        return status == null ? null : status.name();
    }

    @Override
    public Status convertToEntityAttribute(String s) {
        return s == null ? null : Status.valueOf(s);
    }
}
