package ru.javawebinar.topjava.web;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class NullableDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        if (!text.equals("null")) {
            return LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE.withLocale(locale));
        } else {
            return LocalDate.MIN;
        }
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object.format(DateTimeFormatter.ISO_LOCAL_DATE.withLocale(locale));
    }
}