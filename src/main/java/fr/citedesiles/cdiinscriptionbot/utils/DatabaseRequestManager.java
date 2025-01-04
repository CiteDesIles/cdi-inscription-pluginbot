package fr.citedesiles.cdiinscriptionbot.utils;

import fr.citedesiles.cdiinscriptionbot.CDIInscriptionBotPlugin;
import fr.citedesiles.cdiinscriptionbot.mysql.DatabaseManager;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class DatabaseRequestManager {

    public static boolean isAlreadyLinked(Player player) {
        try(Connection connection = DatabaseManager.MAIN_DB.getDatabaseAccess().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYER WHERE uuid = ?");
            preparedStatement.setString(1, player.getUniqueId().toString());
            return preparedStatement.executeQuery().next();
        } catch (Exception e) {
            CDIInscriptionBotPlugin.instance().getLogger().severe("Error while checking if player is already linked");
        }
        return false;
    }

    public static boolean isAlreadyLinked(String discordID) {
        try(Connection connection = DatabaseManager.MAIN_DB.getDatabaseAccess().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYER WHERE discordID = ?");
            preparedStatement.setString(1, discordID);
            return preparedStatement.executeQuery().next();
        } catch (Exception e) {
            CDIInscriptionBotPlugin.instance().getLogger().severe("Error while checking if player is already linked");
        }
        return false;
    }

    public static void linkPlayer(UUID uuid, String discordId) {
        try(Connection connection = DatabaseManager.MAIN_DB.getDatabaseAccess().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PLAYER (uuid, discordID, team) VALUES (?, ?, ?)");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, discordId);
            preparedStatement.setString(3, "none");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            CDIInscriptionBotPlugin.instance().getLogger().severe("Error while linking player");
        }
    }

    public static void removeLink(String discordID) {
        try(Connection connection = DatabaseManager.MAIN_DB.getDatabaseAccess().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PLAYER WHERE discordID = ?");
            preparedStatement.setString(1, discordID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            CDIInscriptionBotPlugin.instance().getLogger().severe("Error while removing link");
        }
    }

    public static String getTeam(String discordID) {
        try(Connection connection = DatabaseManager.MAIN_DB.getDatabaseAccess().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT team FROM PLAYER WHERE discordID = ?");
            preparedStatement.setString(1, discordID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getString("team");
            }
        } catch (Exception e) {
            CDIInscriptionBotPlugin.instance().getLogger().severe("Error while getting team");
        }
        return null;
    }

    public static void setTeam(String discordID, String team) {
        try(Connection connection = DatabaseManager.MAIN_DB.getDatabaseAccess().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PLAYER SET team = ? WHERE discordID = ?");
            preparedStatement.setString(1, team);
            preparedStatement.setString(2, discordID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            CDIInscriptionBotPlugin.instance().getLogger().severe("Error while setting team");
        }
    }

    public static Integer countPlayers(String team) {
        try(Connection connection = DatabaseManager.MAIN_DB.getDatabaseAccess().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM PLAYER WHERE team = ?");
            preparedStatement.setString(1, team);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            CDIInscriptionBotPlugin.instance().getLogger().severe("Error while getting slots");
        }
        return 0;
    }

    public static Integer getSlots(String team) {
        try(Connection connection = DatabaseManager.MAIN_DB.getDatabaseAccess().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Slots FROM TEAM WHERE name = ?");
            preparedStatement.setString(1, team);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt("slots");
            }
        } catch (Exception e) {
            CDIInscriptionBotPlugin.instance().getLogger().severe("Error while getting slots");
        }
        return 0;
    }
}
