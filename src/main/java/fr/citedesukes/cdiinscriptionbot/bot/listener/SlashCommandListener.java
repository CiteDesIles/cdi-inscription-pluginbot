package fr.citedesukes.cdiinscriptionbot.bot.listener;

import fr.citedesukes.cdiinscriptionbot.CDIInscriptionBotPlugin;
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
                // new LinkCommand().execute(event, plugin);
                break;
            case "unlink":
                // new UnlinkCommand().execute(event, plugin);
                break;
        }
    }
}
