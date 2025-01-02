package fr.citedesiles.cdiinscriptionbot.bot.listener;

import fr.citedesiles.cdiinscriptionbot.CDIInscriptionBotPlugin;
import fr.citedesiles.cdiinscriptionbot.bot.command.LinkCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandListener extends ListenerAdapter {

    private final CDIInscriptionBotPlugin plugin;

    public SlashCommandListener(CDIInscriptionBotPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "ping":
                event.reply("Pong!").queue();
                break;
            case "link":
                LinkCommand.link(event);
                break;
            case "unlink":
                LinkCommand.unlink(event);
                break;
            case "invite":
                event.reply("working on it").queue();
                break;
        }
    }
}
