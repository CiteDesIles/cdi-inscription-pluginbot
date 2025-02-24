package fr.citedesiles.cdiinscriptionbot.listener;

import fr.citedesiles.cdiinscriptionbot.CDIInscriptionBotPlugin;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {
    @EventHandler
    public void on(PlayerJoinEvent event) {
        event.getPlayer().teleport(CDIInscriptionBotPlugin.spawn);
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
        event.getPlayer().sendMessage("§c§lBienvenue sur le serveur !");
        event.getPlayer().sendMessage("§fFaites /link pour lier votre compte Minecraft à Discord !");
        event.getPlayer().sendMessage("§fPuis faites /link <code> sur discord pour complété la liaison !");
    }
}
