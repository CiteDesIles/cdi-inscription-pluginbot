package fr.citedesukes.cdiinscriptionbot.postgresl;

public class DatabaseCredentials {
    private String host;
    private String database;
    private String username;
    private String password;
    private int port;

    public DatabaseCredentials(String host, String database, String username, String password, int port) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public String toURI() {
        final StringBuilder uri = new StringBuilder();
        uri.append("jdbc:postegresql://")
                .append(host)
                .append(":")
                .append(port)
                .append("/")
                .append(database);

        return uri.toString();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}