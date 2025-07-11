package fr.citedesiles.cdiinscriptionbot.bot.command;

import fr.citedesiles.cdiinscriptionbot.CDIInscriptionBotPlugin;
import fr.citedesiles.cdiinscriptionbot.bot.DiscordBot;
import fr.citedesiles.cdiinscriptionbot.objects.RequestToLink;
import fr.citedesiles.cdiinscriptionbot.utils.DatabaseRequestManager;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class LinkCommand {

    public static void link(SlashCommandInteractionEvent event) {
        String code = event.getOption("code").getAsString();
        if(DatabaseRequestManager.isAlreadyLinked(event.getUser().getId())) {
            event.reply("Vous êtes déjà lié").setEphemeral(true).queue();
            return;
        }
        if(!CDIInscriptionBotPlugin.instance().requestManager().hasRequest(code)) {
            event.reply("Code invalide").setEphemeral(true).queue();
            return;
        }

        RequestToLink request = CDIInscriptionBotPlugin.instance().requestManager().getRequest(code);
        if(request.isExpired()) {
            event.reply("Code expiré").setEphemeral(true).queue();
            return;
        }

        DatabaseRequestManager.linkPlayer(request.getUuid(), event.getUser().getId());
        Role role = event.getGuild().getRoleById(DiscordBot.INSCRIT_ROLE_ID);
        event.getGuild().addRoleToMember(event.getMember(), role).queue();
        event.reply("Vous avez été lié").setEphemeral(true).queue();
    }

    public static void unlink(SlashCommandInteractionEvent event) {
        DatabaseRequestManager.removeLink(event.getUser().getId());
        event.reply("Vous avez été délié").setEphemeral(true).queue();
    }
}
