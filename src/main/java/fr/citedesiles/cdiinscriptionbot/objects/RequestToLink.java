package fr.citedesiles.cdiinscriptionbot.objects;

import java.util.UUID;

public class RequestToLink {
    private UUID uuid;
    private String code;
    private int expiration;

    public RequestToLink(UUID uuid, String code, int expiration) {
        this.uuid = uuid;
        this.code = code;
        this.expiration = expiration;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getCode() {
        return code;
    }

    public int getExpiration() {
        return expiration;
    }

    public void removeTick() {
        expiration--;
    }

    public boolean isExpired() {
        return expiration <= 0;
    }
}
