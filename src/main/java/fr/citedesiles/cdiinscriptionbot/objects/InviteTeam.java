package fr.citedesiles.cdiinscriptionbot.objects;

public class InviteTeam {
    private String code;
    private Long discordID;
    private String teamName;
    private int expirationTime = (10 * 20 * 60);

    public InviteTeam(String code, Long discordID, String teamName) {
        this.code = code;
        this.discordID = discordID;
        this.teamName = teamName;
    }

    public Long getDiscordID() {
        return discordID;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setDiscordID(Long discordID) {
        this.discordID = discordID;
    }

    public String getCode() {
        return code;
    }
}
