package com.kancth.nomad.global.converter;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

public class ExceptionMessageConverter {
    public static ResourceBundleMessageSource message = new ResourceBundleMessageSource();

    private static Locale locale = LocaleContextHolder.getLocale();

    static {
        message.setDefaultEncoding("UTF-8");
        message.setBasenames("messages/error");
    }

    public static String getMessage(String code, Object... args) {
        return message.getMessage(code, args, locale);
    }
}
