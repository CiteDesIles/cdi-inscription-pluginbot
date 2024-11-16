package fr.citedesiles.cdiinscriptionbot.postgresl;

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

        config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        config.setMaximumPoolSize(10);
        config.setJdbcUrl(credentials.toURI());
        config.setUsername(credentials.getUsername());
        config.setPassword(credentials.getPassword());
        config.setMaxLifetime(6000L);
        config.setIdleTimeout(3000L);
        config.setLeakDetectionThreshold(3000L);
        config.setConnectionTimeout(1000L);

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