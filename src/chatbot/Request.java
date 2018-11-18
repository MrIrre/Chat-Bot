package chatbot;

import enums.RequestFrom;

/**
 * Класс запроса от пользователя, который хранит:
 *     1. Строку запроса;
 *     2. ID пользователя, который отправил запрос;
 */
public class Request {
    private String userId;
    private String requestString;
    private RequestFrom requestFrom;

    Request(String userId, String request, RequestFrom requestFrom){
        this.userId = userId;
        requestString = request;
        this.requestFrom = requestFrom;
    }

    public String getUserId(){
        return userId;
    }

    public String getRequest() {
        return requestString;
    }

    public RequestFrom getRequestFrom() {
        return requestFrom;
    }
}
