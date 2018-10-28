package chatbot;

/**
 * Класс запроса от пользователя, который хранит:
 *     1. Строку запроса;
 *     2. ID пользователя, который отправил запрос;
 */
public class Request {
    private String userId;
    private String requestString;

    Request(String userId, String request){
        this.userId = userId;
        requestString = request;
    }

    public String getUserId(){
        return userId;
    }

    public String getRequest() {
        return requestString;
    }
}
