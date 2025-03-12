package in.credable.automation.commons.exception;

public final class ColumnNotFoundException extends RuntimeException {
    public ColumnNotFoundException(String columnName) {
        super("Column %s not found in header row".formatted(columnName));
    }
}
