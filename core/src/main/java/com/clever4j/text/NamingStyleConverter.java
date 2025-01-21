package com.clever4j.text;

import com.clever4j.lang.AllNonnullByDefault;

import java.util.Objects;

@AllNonnullByDefault
public class NamingStyleConverter {

    public String convert(String text, NamingStyle style) {
        Objects.requireNonNull(text, "Text cannot be null");
        Objects.requireNonNull(style, "Style cannot be null");

        text = text.strip();

        if (text.isEmpty()) {
            return text;
        }

        String[] words = text.split("[ _\\-]+");

        switch (style) {
            case CAMEL_CASE:
                return toCamelCase(words);
            case PASCAL_CASE:
                return toPascalCase(words);
            case SNAKE_CASE:
                return toSnakeCase(words, false);
            case UPPER_SNAKE_CASE:
                return toSnakeCase(words, true);
            case KEBAB_CASE:
                return toKebabCase(words, false);
            case TRAIN_CASE:
                return toKebabCase(words, true);
            case FLAT_CASE:
                return toFlatCase(words);
            case CAPITALIZED_WORDS:
                return toCapitalizedWords(words);
            default:
                throw new UnsupportedOperationException("Unknown naming style: " + style);
        }
    }

    private String toCamelCase(String[] words) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i == 0) {
                result.append(words[i].toLowerCase());
            } else {
                result.append(capitalize(words[i]));
            }
        }
        return result.toString();
    }

    private String toPascalCase(String[] words) {
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(capitalize(word));
        }
        return result.toString();
    }

    private String toSnakeCase(String[] words, boolean upperCase) {
        String result = String.join("_", words).toLowerCase();
        return upperCase ? result.toUpperCase() : result;
    }

    private String toKebabCase(String[] words, boolean upperCase) {
        String result = String.join("-", words).toLowerCase();
        return upperCase ? result.toUpperCase() : result;
    }

    private String toFlatCase(String[] words) {
        return String.join("", words).toLowerCase();
    }

    private String toCapitalizedWords(String[] words) {
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(capitalize(word));
        }
        return result.toString();
    }

    private String capitalize(String word) {
        if (word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    public enum NamingStyle {
        CAMEL_CASE,          // camelCaseExample
        PASCAL_CASE,         // PascalCaseExample
        SNAKE_CASE,          // snake_case_example
        UPPER_SNAKE_CASE,    // UPPER_SNAKE_CASE
        KEBAB_CASE,          // kebab-case-example
        TRAIN_CASE,          // TRAIN-CASE-EXAMPLE
        FLAT_CASE,           // flatcaseexample
        CAPITALIZED_WORDS;   // CapitalizedWordsExample
    }
}
