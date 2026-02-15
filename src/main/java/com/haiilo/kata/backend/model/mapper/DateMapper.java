package com.haiilo.kata.backend.model.mapper;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateMapper {

    private static final String DATE_FORMAT = "dd-MM-yyyy'T'HH:mm:ss.SSS'Z'";

    public Date asDate(String date) {
        try {
            return date != null ? new SimpleDateFormat(DATE_FORMAT).parse(date) : null;
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String asString(Date date) {
        return date != null ? new SimpleDateFormat(DATE_FORMAT).format(date) : null;
    }
}
