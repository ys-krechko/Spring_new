package com.it.component;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Class with custom getMessage method, that takes locale from a LocaleContext instance for the current thread
 */
@RequiredArgsConstructor
@Component
public class LocalizedMessageSource {

    private final MessageSource messageSource;

    private List<Locale> localeList = Arrays.asList(new Locale("ru"), new Locale("en"));

    /**
     * Resolves the message
     *
     * @param messageCode -the code to lookup up
     * @param arguments   - an array of arguments that will be filled in for params within the message
     * @return - the resolved message if the lookup was successful;
     * otherwise the default message passed as a parameter
     */
    public String getMessage(String messageCode, Object[] arguments) {
        Locale locale = LocaleContextHolder.getLocale();
        locale = localeList.contains(locale) ? locale : Locale.getDefault();
        return messageSource.getMessage(messageCode, arguments, locale);
    }
}
