package fr.citedesukes.cdiinscriptionbot.postgresl;

import fr.citedesukes.cdiinscriptionbot.CDIInscriptionBotPlugin;

public enum DatabaseManager {

    MAIN_DB(new DatabaseCredentials(
            CDIInscriptionBotPlugin.instance().config().getString("database.host"),
            CDIInscriptionBotPlugin.instance().config().getString("database.name"),
            CDIInscriptionBotPlugin.instance().config().getString("database.user"),
            CDIInscriptionBotPlugin.instance().config().getString("database.password"),
            CDIInscriptionBotPlugin.instance().config().getInt("database.port")
    ));

    private DatabaseAccess databaseAccess;

    DatabaseManager(DatabaseCredentials credentials) {
        this.databaseAccess = new DatabaseAccess(credentials);
    }

    public DatabaseAccess getDatabaseAccess() {
        return databaseAccess;
    }

    public static void initAllDataBaseConnections() {
        for(DatabaseManager databaseManager : values()) {
            databaseManager.getDatabaseAccess().initPool();
        }
    }

    public static void closeAllDataBaseConnections() {
        for(DatabaseManager databaseManager : values()) {
            databaseManager.getDatabaseAccess().closePool();
        }
    }
}
