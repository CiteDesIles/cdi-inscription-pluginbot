package fr.citedesiles.cdiinscriptionbot.objects;

import fr.citedesiles.cdiinscriptionbot.CDIInscriptionBotPlugin;
import fr.citedesiles.cdiinscriptionbot.bot.DiscordBot;
import fr.citedesiles.cdiinscriptionbot.utils.DatabaseRequestManager;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InviteManager {
    List<InviteTeam> invites = new ArrayList<>();

    public void tick() {
        for (InviteTeam invite : invites) {
            invite.setExpirationTime(invite.getExpirationTime() - 1);
        }
    }

    public void addInvite(InviteTeam invite) {
        invites.add(invite);
    }

    public InviteTeam getInvite(String code) {
        for (InviteTeam invite : invites) {
            if (invite.getCode().equals(code)) {
                return invite;
            }
        }
        return null;
    }

    public List<InviteTeam> getInvites() {
        return new ArrayList<>(invites);
    }

    public boolean isInvited(Long discordID) {
        for (InviteTeam invite : invites) {
            if (invite.getDiscordID().equals(discordID)) {
                return true;
            }
        }
        return false;
    }

    public int countInviteTeam(String teamName) {
        int count = 0;
        for (InviteTeam invite : invites) {
            if (invite.getTeamName().equals(teamName)) {
                count++;
            }
        }
        return count;
    }

    public void acceptInvite(String code, ButtonInteractionEvent event) {
        InviteTeam invite = getInvite(code);
        if(invite == null) {
            event.reply("L'invitation n'existe pas/a expiré").setEphemeral(true).queue();
        }
        invites.remove(invite);
        String roleID = DiscordBot.TEAM_AND_ROLE_IDS.get(invite.getTeamName());

        Objects.requireNonNull(CDIInscriptionBotPlugin.instance().bot().getJda().getGuildById(DiscordBot.GUILD_ID)).
            addRoleToMember(event.getUser(), Objects.requireNonNull(CDIInscriptionBotPlugin.instance().bot().getJda().getRoleById(roleID))).queue();

        DatabaseRequestManager.setTeam(String.valueOf(invite.getDiscordID()), invite.getTeamName());
    }

    public void denyInvite(String code , ButtonInteractionEvent event) {
        InviteTeam invite = getInvite(code);
        if(invite == null) {
            event.reply("L'invitation n'existe pas/a expiré").setEphemeral(true).queue();
        }
        invites.remove(invite);
    }
}
