package chatbot;

import interfaces.Input;
import interfaces.Output;

import java.util.*;

/**
 * Главный цикл чат-бота, который на каждой итерации обрабатывает запрос пользователя.
 */
public class MainLoop {
    private Map<String, User> users = new HashMap<>();
    private Deque<Request> requests = new ArrayDeque<>();
    private RequestHandler requestHandler = new RequestHandler();

    private Request inputtedRequest;

    /**
     * Запуск главного цикла.
     * @param input Класс, реализующий Input интерфейс.
     * @param output Класс, реализующий Output интерфейс.
     */
    void runMainLoop(Input input, Output output) throws Exception {

        while (true){
            Thread.sleep(10);

            inputtedRequest = input.getRequest();

            if (inputtedRequest != null && !inputtedRequest.getRequest().equals("")){
                requests.push(inputtedRequest);
            }

            if (requests.isEmpty()){
                continue;
            }

            Request curRequest = requests.pop();

            if (!users.containsKey(curRequest.getUserId())){
                users.put(curRequest.getUserId(), new User(curRequest.getUserId()));
            }

            User curUser = users.get(curRequest.getUserId());
            ArrayList<String> curAnswer = requestHandler.getAnswer(curRequest.getRequest(), curUser);
            output.print(curUser.getId(), curAnswer);
        }
    }
}
