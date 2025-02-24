package fr.citedesiles.cdiinscriptionbot.listener;

import fr.citedesiles.cdiinscriptionbot.CDIInscriptionBotPlugin;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnMove implements Listener {
    @EventHandler
    public void on(PlayerMoveEvent event) {
        if(event.getPlayer().isOp()) {
            return;
        }
        if(event.getPlayer().getLocation().distance(CDIInscriptionBotPlugin.spawn) >= 75 && event.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) {
            event.getPlayer().teleport(CDIInscriptionBotPlugin.spawn);
        }
    }
}
