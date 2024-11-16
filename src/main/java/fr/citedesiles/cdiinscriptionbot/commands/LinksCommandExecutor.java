package fr.citedesiles.cdiinscriptionbot.commands;

import fr.citedesiles.cdiinscriptionbot.CDIInscriptionBotPlugin;
import fr.citedesiles.cdiinscriptionbot.objects.RequestToLink;
import fr.citedesiles.cdiinscriptionbot.utils.DatabaseRequestManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LinksCommandExecutor implements CommandExecutor {

    private final CDIInscriptionBotPlugin plugin;

    public LinksCommandExecutor(CDIInscriptionBotPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        String possibleChars = "abcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()_+-=[]{}|;:,.<>?ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String code= "";

        if(DatabaseRequestManager.isAlreadyLinked(player)) {
            player.sendMessage("Vous êtes déjà lié à un compte discord");
            return true;
        }

        for (int i = 0; i < 6; i++) {
            code += possibleChars.charAt((int) (Math.random() * possibleChars.length()));
        }

        if(plugin.requestManager().hasRequest(player.getUniqueId())) {
            player.sendMessage("Vous avez déjà un code en attente");
            return true;
        }

        RequestToLink request = new RequestToLink(player.getUniqueId(), code, 20*60*10);
        player.sendMessage("Votre code est : " + code);
        player.sendMessage("Il expire dans 10 minutes");

        plugin.requestManager().addRequest(request);
        return true;
    }
}
