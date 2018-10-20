package chatbot;

import enums.Version;

public class Request {
    private String UserId;
    private String InputRequest;
    private enums.Version Version;

    Request(String userId, String request, Version version){
        UserId = userId;
        InputRequest = request;
        Version = version;
    }

    public String getUserId(){
        return UserId;
    }

    public String getRequest() {
        return InputRequest;
    }

    public Version getVersion() {
        return Version;
    }
}
