package com.clever4j.validators;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_PATTERN =
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public boolean isValid(@Nonnull String email) {
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
