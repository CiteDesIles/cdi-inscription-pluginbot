package fr.citedesiles.cdiinscriptionbot.bot.command;

import fr.citedesiles.cdiinscriptionbot.CDIInscriptionBotPlugin;
import fr.citedesiles.cdiinscriptionbot.bot.DiscordBot;
import fr.citedesiles.cdiinscriptionbot.objects.InviteTeam;
import fr.citedesiles.cdiinscriptionbot.utils.DatabaseRequestManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;

public class InviteCommand {



    public static void invite(SlashCommandInteractionEvent event) {
        String possibleChars = "abcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()_+-=[]{}|;:,.<>?ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String code= "";
        for (int i = 0; i < 6; i++) {
            code += possibleChars.charAt((int) (Math.random() * possibleChars.length()));
        }
        Member member = event.getMember();
        List<Role> roles = member.getRoles();
        boolean isTeamLeader = false;
        for(Role role : roles) {
            if(role.getId().equals(DiscordBot.CHEF_ROLE_ID)) {
                isTeamLeader = true;
            }
        }
        if(!isTeamLeader) {
            event.reply("Vous n'êtes pas chef d'équipe").setEphemeral(true).queue();
            return;
        }
        Member target = event.getOption("user").getAsMember();
        boolean isTargetInscrit = false;
        boolean isAlreadyInvited = false;
        boolean isAlreadyInTeam = false;
        List<Role> targetRoles = target.getRoles();
        for(Role role : targetRoles) {
            if(role.getId().equals(DiscordBot.INSCRIT_ROLE_ID)) {
                isTargetInscrit = true;
            }
            if(CDIInscriptionBotPlugin.instance().inviteManager().isInvited(target.getIdLong())) {
                isAlreadyInvited = true;
            }
            for(String roleID : DiscordBot.TEAM_ROLE_IDS) {
                if(role.getId().equals(roleID)) {
                    isAlreadyInTeam = true;
                }
            }
        }
        if(!isTargetInscrit) {
            event.reply("La personne n'est pas inscrite").setEphemeral(true).queue();
            return;
        }
        if(isAlreadyInvited) {
            event.reply("La personne a déjà été invitée").setEphemeral(true).queue();
            return;
        }
        if(isAlreadyInTeam) {
            event.reply("La personne est déjà dans une équipe").setEphemeral(true).queue();
            return;
        }

        String teamName = DatabaseRequestManager.getTeam(member.getId());
        if(CDIInscriptionBotPlugin.instance().inviteManager().countInviteTeam(teamName) + DatabaseRequestManager.countPlayers(teamName ) >= DatabaseRequestManager.getSlots(teamName)) {
            event.reply("Vous n'avez pas assez de slots/trop d'invitation en attente").setEphemeral(true).queue();
            return;
        }

        InviteTeam invite = new InviteTeam(code, target.getIdLong(), teamName);
        String finalCode = code;
        target.getUser().openPrivateChannel().queue(channel -> {
        String message = "Vous avez été invité dans l'équipe " + teamName + " par " + member.getUser().getAsTag() + "\n";
        channel.sendMessage(message).addActionRow(
            Button.primary("accept-" + finalCode, "Accepter"),
            Button.danger("decline-" + finalCode, "Refuser")
        ).queue();
        });
        CDIInscriptionBotPlugin.instance().inviteManager().addInvite(invite);
        event.reply("Invitation envoyée").setEphemeral(true).queue();
    }
}
