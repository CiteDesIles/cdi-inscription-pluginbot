package fr.citedesiles.cdiinscriptionbot.bot.listener;

import fr.citedesiles.cdiinscriptionbot.CDIInscriptionBotPlugin;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ButtonsInteractionListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if(event.getButton().getId().contains("accept")) {
            String id = event.getButton().getId().split("-")[1];
            CDIInscriptionBotPlugin.instance().inviteManager().acceptInvite(id, event);
            event.reply("Vous avez accepté l'invitation").setEphemeral(true).queue();
        } else {
            String id = event.getButton().getId().split("-")[1];
            CDIInscriptionBotPlugin.instance().inviteManager().denyInvite(id, event);
            event.reply("Vous avez refusé l'invitation").setEphemeral(true).queue();
        }
    }

}
