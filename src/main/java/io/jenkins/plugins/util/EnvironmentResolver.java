package io.jenkins.plugins.util;

import org.apache.commons.lang3.StringUtils;

import edu.hm.hafner.util.VisibleForTesting;
import edu.umd.cs.findbugs.annotations.CheckForNull;

import hudson.EnvVars;
import hudson.Util;

/**
 * Resolves environment parameters in a string value.
 *
 * @author Ullrich Hafner
 */
public class EnvironmentResolver {
    /** Maximum number of times that the environment expansion is executed. */
    private static final int RESOLVE_VARIABLE_DEPTH_DEFAULT = 10;

    private final int resolveVariablesDepth;

    /**
     * Creates a new instance of {@link EnvironmentResolver}. Attempts up to {@link #RESOLVE_VARIABLE_DEPTH_DEFAULT}
     * times to resolve a variable.
     */
    public EnvironmentResolver() {
        this(RESOLVE_VARIABLE_DEPTH_DEFAULT);
    }

    @VisibleForTesting
    EnvironmentResolver(final int resolveVariablesDepth) {
        this.resolveVariablesDepth = resolveVariablesDepth;
    }

    /**
     * Resolves build parameters in the specified string value to {@link #resolveVariablesDepth} times.
     *
     * @param environment
     *         environment variables
     * @param nonExpandedValue
     *         the value to expand
     *
     * @return the expanded value
     */
    public String expandEnvironmentVariables(@CheckForNull final EnvVars environment, final String nonExpandedValue) {
        var expanded = nonExpandedValue;
        if (environment != null && !environment.isEmpty()) {
            for (int i = 0; i < resolveVariablesDepth && StringUtils.isNotBlank(expanded); i++) {
                var old = expanded;
                expanded = expand(environment, expanded);
                if (old.equals(expanded)) {
                    return expanded;
                }
            }
        }
        return expanded;
    }

    private String expand(final EnvVars environment, final String expanded) {
        return StringUtils.defaultString(Util.replaceMacro(expanded, environment));
    }
}
