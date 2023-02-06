package Controller;

import Models.*;

public class Request {

    private int requestOwnerId;
    private String request;

    public Request (int requestOwner, String requests) {
        this.requestOwnerId = requestOwner;
        this.request = requests;
    }

    public int getRequestOwner() {
        return requestOwnerId;
    }

    public void setRequestOwner(int requestOwner) {
        this.requestOwnerId = requestOwner;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String requests) {
        this.request = requests;
    }
}
