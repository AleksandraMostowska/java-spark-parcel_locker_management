package mostowska.aleksandra.db_management;

import lombok.RequiredArgsConstructor;
import mostowska.aleksandra.db_management.model.RowData;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Component responsible for managing database operations such as creating, dropping,
 * inserting, and updating tables using JDBI.
 *
 * <p>This class provides methods to execute common database schema management tasks,
 * including table creation and deletion, as well as inserting and updating data.</p>
 */
@Component
@RequiredArgsConstructor
public class DbManagement {
    private final Jdbi jdbi;

    /**
     * Creates a new table in the database with the specified name and columns.
     *
     * @param tableName The name of the table to be created.
     * @param pk        The name of the primary key column.
     * @param columns   A map where the keys are column names and the values are
     *                  column types (e.g., "VARCHAR(255)", "INTEGER").
     */
    public void createTable(String tableName, String pk, Map<String, String> columns) {
        var createTableSql = """
            create table if not exists %s (
                %s integer primary key auto_increment,
                %s
            )
        """.formatted(tableName, pk, createColumnsForNewTable(columns));
        System.out.println(createTableSql);
        jdbi.useHandle(handle -> handle.execute(createTableSql));
    }

    /**
     * Drops an existing table from the database.
     *
     * @param name The name of the table to be dropped.
     */
    public void dropTable(String name) {
        jdbi.useHandle(handle
                -> handle.execute("drop table if exists " + name));
    }

    /**
     * Inserts data into a specified table.
     *
     * @param tableName The name of the table into which data will be inserted.
     * @param data      A list of RowData objects representing the rows to be inserted.
     */
    public void insert(String tableName, List<RowData<?>> data) {
        var insertSql = "insert into %s %s;".formatted(tableName, RowData.toInsertSql(data));
        jdbi.useHandle(handle -> handle.execute(insertSql));
    }

    /**
     * Updates data in a specified table based on the given ID.
     *
     * @param tableName The name of the table to be updated.
     * @param id        The ID of the row to be updated.
     * @param data      A list of RowData objects representing the updated values.
     */
    public void update(String tableName, long id, List<RowData<?>> data) {
        var updateSql = "update %s set %s where id = %d".formatted(
                tableName,
                RowData.toUpdateSql(data),
                id
        );
        jdbi.useHandle(handle -> handle.execute(updateSql));
    }

    /**
     * Constructs the column definitions for creating a new table.
     *
     * @param columns A map where the keys are column names and the values are column types.
     * @return A string representing the column definitions for a CREATE TABLE SQL statement.
     */
    private static String createColumnsForNewTable(Map<String, String> columns) {
        return columns
                .entrySet()
                .stream()
                .map(e -> e.getKey() + " " + e.getValue())
                .collect(Collectors.joining(", "));
    }
}
