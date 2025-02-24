package fr.citedesiles.cdiinscriptionbot.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnDamage implements Listener {
    @EventHandler
    public void on(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player player) {
            event.setCancelled(true);
        }
    }
}
