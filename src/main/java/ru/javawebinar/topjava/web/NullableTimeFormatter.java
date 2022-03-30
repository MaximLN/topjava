package ru.javawebinar.topjava.web;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class NullableTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        if (!text.equals("null")) {
            System.out.println("!text.equals(null)");
            return LocalTime.parse(text, DateTimeFormatter.ISO_LOCAL_TIME.withLocale(locale));
        } else {
            System.out.println("text.equals(null)");
            return null;
        }
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        System.out.println("---------------------print ");
        return object.format(DateTimeFormatter.ISO_LOCAL_TIME.withLocale(locale));
    }
}