package fr.citedesiles.cdiinscriptionbot.listener;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnBreak implements Listener {
    @EventHandler
    @SuppressWarnings("deprecation")
    public void on(BlockBreakEvent event) {
        if(!event.getPlayer().isOp()) {
            event.setCancelled(true);
            event.getPlayer().spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                new TextComponent("§cVous n'avez pas la permission de casser des blocs !")
            );
        }
    }
}
