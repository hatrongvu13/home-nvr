package com.xxx.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CustomLocaleResolver extends AcceptHeaderLocaleResolver {
    private static final List<Locale> LOCALES = Arrays.asList(new Locale("en"), new Locale("vi"));
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-language");
        return headerLang == null || headerLang.isEmpty()
                ? Locale.forLanguageTag("vi")
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), LOCALES);
    }
}
