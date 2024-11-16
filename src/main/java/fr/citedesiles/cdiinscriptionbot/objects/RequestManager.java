package fr.citedesiles.cdiinscriptionbot.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RequestManager {
    List<RequestToLink> requests = new ArrayList<>();

    public void addRequest(RequestToLink request) {
        requests.add(request);
    }

    public void removeRequest(RequestToLink request) {
        requests.remove(request);
    }

    public RequestToLink getRequest(String code) {
        for (RequestToLink request : requests) {
            if (request.getCode().equals(code)) {
                return request;
            }
        }
        return null;
    }

    public RequestToLink getRequest(UUID uuid) {
        for (RequestToLink request : requests) {
            if (request.getUuid().equals(uuid)) {
                return request;
            }
        }
        return null;
    }

    public boolean hasRequest(UUID uuid) {
        for (RequestToLink request : requests) {
            if (request.getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRequest(String code) {
        for (RequestToLink request : requests) {
            if (request.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    public List<RequestToLink> getRequests() {
        return requests;
    }

    public void removeAtickToAllRequest() {
        for (RequestToLink request : requests) {
            request.removeTick();
            if(request.isExpired()) {
                requests.remove(request);
            }
        }
    }
}
