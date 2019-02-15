package com.github.marchenkoprojects.prettyjdbc.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class processes a SQL query with named parameters to retrieve its native form and all named parameters.
 *
 * @author Oleg Marchenko
 */
public class NamedParameterQueryProcessor {
    private static final char[] PARAMETER_SEPARATORS = {
            '"', '\'', ':', '&', ',', ';', '(', ')', '|', '=', '+', '-', '*', '%', '/', '\\', '<', '>', '^'
    };
    private static final String NATIVE_SQL_PARAMETER = "?";

    private final String query;
    private final StringBuilder nativeQuery;
    private final List<String> parameters;

    public NamedParameterQueryProcessor(String query) {
        this.query = query;
        this.nativeQuery = new StringBuilder(query);
        this.parameters = new ArrayList<>(8);
    }

    /**
     * Performs query processing with named parameters.
     */
    public void process() {
        doProcess();
    }

    /**
     * Returns the native SQL query form after processing.
     *
     * @return the native query form or current raw query,
     *         if the method {@link NamedParameterQueryProcessor#process()} was not executed
     */
    public String getNativeQuery() {
        return nativeQuery.toString();
    }

    /**
     * Returns all named parameters for the current query.
     *
     * @return list of named parameters
     */
    public List<String> getParameters() {
        return parameters;
    }

    private void doProcess() {
        char[] chars = query.toCharArray();

        int i = 0;
        int length = query.length();

        int offset = 0;

        while (i < length) {
            char c = chars[i];
            if (c == ':') {
                int j = i + 1;
                char n = chars[j];

                if (j < length) {
                    // Postgres style "::" casting operator should be skipped.
                    if (n == ':') {
                        i = i + 2;
                        continue;
                    }

                    // :{x} style parameter.
                    if (n == '{') {
                        while (j < length && chars[j] != '}') {
                            j++;
                            if (j < length && (chars[j] == ':' || chars[j] == '{')) {
                                throw new IllegalStateException("Named parameter contains invalid character '" +
                                        chars[j] + "' at position " + i + " in statement: " + query);
                            }
                        }

                        if (j >= length) {
                            throw new IllegalStateException("Non-terminated named parameter declaration at position " + i
                                    + " in statement: " + query);
                        }

                        if (j - i > 3) {
                            nativeQuery.replace(i - offset, j - offset + 1, NATIVE_SQL_PARAMETER);
                            parameters.add(query.substring(i + 2, j));

                            offset += j - i;
                        }

                        j++;
                    }
                    else {
                        while (j < length && !isParameterSeparator(chars[j])) {
                            j++;
                            if (j < length && (chars[j] == ':' || chars[j] == '}')) {
                                throw new IllegalStateException("Named parameter contains invalid character '" +
                                        chars[j] + "' at position " + i + " in statement: " + query);
                            }
                        }

                        if (j - i > 1) {
                            nativeQuery.replace(i - offset, j - offset, NATIVE_SQL_PARAMETER);
                            parameters.add(query.substring(i + 1, j));

                            offset += j - i - 1;
                        }
                    }

                    i = j - 1;
                }
            }

            i++;
        }
    }

    private static boolean isParameterSeparator(char c) {
        if (Character.isWhitespace(c)) return true;

        for (char parameterSeparator: PARAMETER_SEPARATORS) {
            if (c == parameterSeparator) return true;
        }
        return false;
    }
}
