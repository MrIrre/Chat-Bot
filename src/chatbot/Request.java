package chatbot;

import enums.Version;

/**
 * Класс запроса от пользователя, который хранит:
 *     1. Строку запроса;
 *     2. ID пользователя, который отправил запрос;
 *     3. Откуда пришёл запрос(Консоль, пока что);
 */
public class Request {
    private String userId;
    private String requestString;
    private enums.Version version;

    Request(String userId, String request, Version version){
        this.userId = userId;
        requestString = request;
        this.version = version;
    }

    public String getUserId(){
        return userId;
    }

    public String getRequest() {
        return requestString;
    }

    public Version getVersion() {
        return version;
    }
}
