package fr.citedesiles.cdiinscriptionbot.bot;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import fr.citedesiles.cdiinscriptionbot.CDIInscriptionBotPlugin;
import fr.citedesiles.cdiinscriptionbot.bot.listener.ButtonsInteractionListener;
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

    public final static String INSCRIT_ROLE_ID = "1284899575856889866";
    public final static String CHEF_ROLE_ID = "1324414953385496586";
    public final static List<String> TEAM_ROLE_IDS = List.of(
        "1284899003707818135",
        "1284899040529485824",
        "1284899104736018457",
        "1284899164718628946",
        "1284899241768259755",
        "1284899295383912628",
        "1284899359179280555",
        "1284899414586163291",
        "1284899465341435964",
        "1284899504977350777"
    );

    public static HashMap<String, String> TEAM_AND_ROLE_IDS = new HashMap<>();

    public static String GUILD_ID = "1284898976281264239";

    public DiscordBot(String token, CDIInscriptionBotPlugin plugin) {
        this.token = token;
        this.plugin = plugin;
    }

    public void start() {
        jda = JDABuilder.createLight(token, EnumSet.allOf(GatewayIntent.class))
            .enableIntents(GatewayIntent.GUILD_MEMBERS)
            .setActivity(Activity.playing("Citée des Îles"))
            .addEventListeners(new SlashCommandListener(plugin))
            .addEventListeners(new ButtonsInteractionListener())
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

        commands.addCommands(
            Commands.slash("invite", "Get an invite link to invite a player to your team (team leader only)")
                .setNameLocalization(DiscordLocale.FRENCH, "invite")
                .setDescriptionLocalization(DiscordLocale.FRENCH, "Inviter un joueur dans votre équipe (chef d'équipe uniquement)")
                .addOption(OptionType.USER, "user", "The user you want to invite", true)
                    .setDescriptionLocalization(DiscordLocale.FRENCH, "L'utilisateur que vous voulez inviter")
        );

        commands.queue();

        TEAM_AND_ROLE_IDS.put("adminTeam", "1284899003707818135");
        TEAM_AND_ROLE_IDS.put("modTeam", "1284899040529485824");
        TEAM_AND_ROLE_IDS.put("team1", "1284899104736018457");
        TEAM_AND_ROLE_IDS.put("team2", "1284899164718628946");
        TEAM_AND_ROLE_IDS.put("team3", "1284899241768259755");
        TEAM_AND_ROLE_IDS.put("team4", "1284899295383912628");
        TEAM_AND_ROLE_IDS.put("team5", "1284899359179280555");
        TEAM_AND_ROLE_IDS.put("team6", "1284899414586163291");
        TEAM_AND_ROLE_IDS.put("team7", "1284899465341435964");
        TEAM_AND_ROLE_IDS.put("team8", "1284899504977350777");
    }

    public void stop() {
        jda.shutdownNow();
    }

    public JDA getJda() {
        return jda;
    }

}