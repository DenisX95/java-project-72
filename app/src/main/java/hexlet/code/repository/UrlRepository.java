package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {
    public static void save(Url url) throws SQLException {
        var createdAt = LocalDateTime.now();
        url.setCreatedAt(createdAt);

        String sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, url.getName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(createdAt));
            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                url.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static Optional<Url> find(Long id) throws SQLException {
        var sql = "SELECT id, name, created_at FROM urls WHERE id = ?";

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

                var url = new Url(id, name, createdAt);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }

    public static boolean isExist(String name) throws SQLException {
        var sql = "SELECT id FROM urls WHERE name = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            var resultSet = stmt.executeQuery();
            return resultSet.next();
        }
    }

    public static List<Url> getEntities() throws SQLException {
        var sql = "SELECT "
                    + "u.id AS id, "
                    + "u.name AS name, "
                    + "uc.status_code AS last_status_code, "
                    + "uc.created_at AS last_check "
                + "FROM urls u "
                + "LEFT JOIN url_checks uc "
                    + "ON uc.id = ( "
                        + "SELECT MAX(id) "
                        + "FROM url_checks "
                        + "WHERE url_checks.url_id = u.id)";

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            var result = new ArrayList<Url>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");

                Timestamp lastCheckRaw = resultSet.getTimestamp("last_check");
                LocalDateTime lastCheck = lastCheckRaw != null ? lastCheckRaw.toLocalDateTime() : null;

                var lastStatusCode = resultSet.getObject("last_status_code", Integer.class);

                var url = new Url(id, name, lastCheck, lastStatusCode);
                result.add(url);
            }
            return result;
        }
    }

    public static void removeAll() throws SQLException {
        var sql = "DELETE FROM urls";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.execute();
        }
    }
}
