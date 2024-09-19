package mostowska.aleksandra.db_management.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.teeing;

/**
 * Represents a data row for database operations, encapsulating a column name and its associated value.
 * Provides utility methods for generating SQL statements for inserting and updating rows in a database.
 *
 * @param <T> The type of the value associated with the column.
 * @param column The name of the column.
 * @param value The value to be inserted or updated in the column.
 */
public record RowData<T>(String column, T value) {

    /**
     * Generates an SQL statement for inserting multiple rows into a table.
     *
     * <p>The method produces an SQL INSERT statement by converting a list of RowData objects
     * into a comma-separated list of column names and corresponding values.</p>
     *
     * @param data A list of RowData objects representing the columns and values to be inserted.
     * @return An SQL INSERT statement in the format: "INSERT INTO table_name (column1, column2, ...) VALUES (value1, value2, ...)".
     */
    public static String toInsertSql(List<RowData<?>> data) {
        return data
                .stream()
                .collect(teeing(
                                collectingAndThen(
                                        Collectors.mapping(RowData::column, Collectors.toList()),
                                        items -> String.join(", ", items)),
                                collectingAndThen(
                                        Collectors.mapping(row -> toSqlValue(row.value),
                                                Collectors.toList()),
                                        items -> String.join(", ", items)),
                                "(%s) values (%s)"::formatted
                        )
                );
    }

    /**
     * Generates an SQL statement for updating a row in a table.
     *
     * <p>The method produces an SQL UPDATE statement by converting a list of RowData objects
     * into a comma-separated list of column assignments.</p>
     *
     * @param data A list of RowData objects representing the columns and their new values for the update.
     * @return An SQL UPDATE statement in the format: "UPDATE table_name SET column1=value1, column2=value2, ...".
     */
    public static String toUpdateSql(List<RowData<?>> data) {
        return data
                .stream()
                .map(row -> "%s=%s".formatted(
                        row.column,
                        toSqlValue(row.value)
                )).collect(Collectors.joining(", "));
    }

    /**
     * Converts a value to its appropriate SQL representation.
     *
     * <p>Values are converted based on their type:
     * <ul>
     *   <li>Enum types are converted to their name in single quotes.</li>
     *   <li>Strings, LocalDate, and LocalDateTime types are enclosed in single quotes.</li>
     *   <li>Other types are converted to their string representation.</li>
     * </ul>
     * </p>
     *
     * @param value The value to be converted to an SQL-compatible format.
     * @param <T> The type of the value.
     * @return The SQL representation of the value.
     */
    private static <T> String toSqlValue(T value) {
        if (value instanceof Enum<?>) {
            return "'" + ((Enum<?>) value).name() + "'";
        }
        if (List.of(
                        String.class,
                        LocalDate.class,
                        LocalDateTime.class)
                .contains(value.getClass())) {
            return "'" + value + "'";
        }
        return String.valueOf(value);
    }
}