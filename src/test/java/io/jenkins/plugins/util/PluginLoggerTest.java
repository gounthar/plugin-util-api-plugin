package io.jenkins.plugins.util;

import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.*;
import static java.util.Collections.*;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link PluginLogger}.
 *
 * @author Ullrich Hafner
 */
class PluginLoggerTest {
    private static final String LOG_MESSAGE = "Hello ToolLogger!";
    private static final String TOOL_NAME = "test";
    private static final String EXPECTED_TOOL_PREFIX = "[test]";
    private static final String FIRST_MESSAGE = "One";
    private static final String SECOND_MESSAGE = "Two";

    @Test
    @SuppressWarnings("PMD.CloseResource")
    void shouldLogSingleAndMultipleLines() {
        PrintStream printStream = mock(PrintStream.class);
        var logger = new PluginLogger(printStream, TOOL_NAME);

        logger.log(LOG_MESSAGE);

        verify(printStream).println(EXPECTED_TOOL_PREFIX + " " + LOG_MESSAGE);

        var loggerWithBraces = new PluginLogger(printStream, EXPECTED_TOOL_PREFIX);

        loggerWithBraces.log(LOG_MESSAGE);

        verify(printStream, times(2)).println(EXPECTED_TOOL_PREFIX + " " + LOG_MESSAGE);

        logger.logEachLine(emptyList());

        verifyNoMoreInteractions(printStream);

        logger.logEachLine(singletonList(FIRST_MESSAGE));

        verify(printStream).println(EXPECTED_TOOL_PREFIX + " " + FIRST_MESSAGE);

        logger.logEachLine(asList(FIRST_MESSAGE, SECOND_MESSAGE));
        verify(printStream, times(2)).println(EXPECTED_TOOL_PREFIX + " " + FIRST_MESSAGE);
        verify(printStream).println(EXPECTED_TOOL_PREFIX + " " + SECOND_MESSAGE);
    }
}
