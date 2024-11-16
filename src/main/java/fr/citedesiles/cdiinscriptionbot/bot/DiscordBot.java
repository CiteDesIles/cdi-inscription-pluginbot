package fr.citedesiles.cdiinscriptionbot.bot;

import java.util.EnumSet;

import fr.citedesiles.cdiinscriptionbot.CDIInscriptionBotPlugin;
import fr.citedesiles.cdiinscriptionbot.bot.listener.SlashCommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public class DiscordBot {
    private CDIInscriptionBotPlugin plugin;
    JDA jda;
    private final String token;

    public DiscordBot(String token, CDIInscriptionBotPlugin plugin) {
        this.token = token;
        this.plugin = plugin;
    }

    public void start() {
        jda = JDABuilder.createLight(token, EnumSet.allOf(GatewayIntent.class))
            .enableIntents(GatewayIntent.GUILD_MEMBERS)
            .setActivity(Activity.playing("Citée des Îles"))
            .addEventListeners(new SlashCommandListener(plugin))
            .build();

        CommandListUpdateAction commands = jda.updateCommands();

        commands.addCommands(
            Commands.slash("ping", "Pong!")
        );

        commands.addCommands(
            Commands.slash("link", "Link your Minecraft account to your Discord account")
                .addOption(OptionType.STRING, "code", "The code you received in-game", true)
                    .setNameLocalization(DiscordLocale.FRENCH, "code")
                    .setDescriptionLocalization(DiscordLocale.FRENCH, "Le code que vous avez reçu en jeu")
                .setNameLocalization(DiscordLocale.FRENCH, "link")
                .setDescriptionLocalization(DiscordLocale.FRENCH, "Lier votre compte Minecraft à votre compte Discord")
        );

        commands.addCommands(
            Commands.slash("unlink", "Unlink your Minecraft account from your Discord account")
                .setNameLocalization(DiscordLocale.FRENCH, "unlink")
                .setDescriptionLocalization(DiscordLocale.FRENCH, "Délier votre compte Minecraft de votre compte Discord")
        );

        commands.queue();
    }

    public void stop() {
        jda.shutdownNow();
    }

    public JDA getJda() {
        return jda;
    }

}