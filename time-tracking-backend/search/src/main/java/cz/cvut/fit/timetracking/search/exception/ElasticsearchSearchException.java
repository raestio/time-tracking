package cz.cvut.fit.timetracking.search.exception;

public class ElasticsearchSearchException extends RuntimeException {
    public ElasticsearchSearchException() {
    }

    public ElasticsearchSearchException(String message) {
        super(message);
    }

    public ElasticsearchSearchException(String message, Throwable cause) {
        super(message, cause);
    }
}
