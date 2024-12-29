package fr.citedesiles.cdiinscriptionbot.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.citedesiles.cdiinscriptionbot.CDIInscriptionBotPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseAccess {
    private DatabaseCredentials credentials;
    private HikariDataSource hikariDataSource;

    public DatabaseAccess(DatabaseCredentials credentials) {
        this.credentials = credentials;
    }

    private void setupHikari() {
        final HikariConfig config = new HikariConfig();

        config.setMaximumPoolSize(10);
        config.setJdbcUrl(credentials.toURI());
        config.setUsername(credentials.getUsername());
        config.setPassword(credentials.getPassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        this.hikariDataSource = new HikariDataSource(config);
    }

    public void initPool() {
        setupHikari();
    }

    public void closePool() {
        this.hikariDataSource.close();
    }

    public Connection getConnection() throws SQLException {
        if(this.hikariDataSource == null) {
            CDIInscriptionBotPlugin.instance().getLogger().severe("HikariCP is not initialized");
            return null;
        }

        return this.hikariDataSource.getConnection();
    }
}