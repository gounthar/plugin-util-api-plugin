package io.jenkins.plugins.util;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import edu.hm.hafner.util.FilteredLog;

/**
 * A serializable result combined with a logger. Enables remote calls to return a result and a corresponding log.
 *
 * @param <T>
 *         the type of the result
 *
 * @author Ullrich Hafner
 */
public class RemoteResultWrapper<T extends Serializable> extends FilteredLog {
    @Serial
    private static final long serialVersionUID = -6411417555105688927L;

    private final T result;

    /**
     * Creates a new instance of {@link RemoteResultWrapper}.
     *
     * @param title
     *         the title of the error messages
     * @param result
     *         the wrapped result
     */
    public RemoteResultWrapper(final T result, final String title) {
        super(title);

        this.result = result;
    }

    public T getResult() {
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        RemoteResultWrapper<?> that = (RemoteResultWrapper<?>) o;

        return Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        int result1 = super.hashCode();
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }
}
