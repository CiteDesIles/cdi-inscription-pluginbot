package fr.citedesukes.cdiinscriptionbot.utils;

import fr.citedesukes.cdiinscriptionbot.CDIInscriptionBotPlugin;
import fr.citedesukes.cdiinscriptionbot.postgresl.DatabaseManager;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DatabaseRequestManager {

    public static boolean isAlreadyLinked(Player player) {
        try(Connection connection = DatabaseManager.MAIN_DB.getDatabaseAccess().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM player WHERE uuid = ?");
            preparedStatement.setString(1, player.getUniqueId().toString());
            return preparedStatement.executeQuery().next();
        } catch (Exception e) {
            CDIInscriptionBotPlugin.instance().getLogger().severe("Error while checking if player is already linked");
        }
        return false;
    }

    public static void linkPlayer(Player player, String discordId) {
        try(Connection connection = DatabaseManager.MAIN_DB.getDatabaseAccess().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO player (uuid, discordid, team) VALUES (?, ?, ?)");
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, discordId);
            preparedStatement.setString(3, "none");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            CDIInscriptionBotPlugin.instance().getLogger().severe("Error while linking player");
        }
    }

    public static void removeLink(String discordID) {
        try(Connection connection = DatabaseManager.MAIN_DB.getDatabaseAccess().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM player WHERE discordid = ?");
            preparedStatement.setString(1, discordID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            CDIInscriptionBotPlugin.instance().getLogger().severe("Error while removing link");
        }
    }
}
