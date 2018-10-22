package chatbot;

import enums.Version;
import interfaces.Input;
import interfaces.Output;

import java.util.*;

/**
 * Главный цикл чат-бота, который на каждой итерации обрабатывает запрос пользователя.
 */
public class MainLoop {
    private static Map<String, User> users = new HashMap<>();
    private static Deque<Request> requests = new ArrayDeque<>();
    private static RequestHandler requestHandler = new RequestHandler();

    /**
     * Запуск главного цикла.
     * @param input Класс, реализующий Input интерфейс.
     * @param output Класс, реализующий Output интерфейс.
     */
    public void runMainLoop(Input input, Output output) throws Exception {

        while (true){
            Request inputedRequest = input.getRequest();

            if (!inputedRequest.getRequest().equals("")){
                requests.push(inputedRequest);
            }

            if (requests.isEmpty()){
                continue;
            }

            Request curRequest = requests.pop();

            if (!users.containsKey(curRequest.getUserId())){
                Version curUserVersion = curRequest.getVersion();
                users.put(curRequest.getUserId(), new User(curRequest.getUserId(), curUserVersion));
            }

            User curUser = users.get(curRequest.getUserId());
            ArrayList<String> curAnswer = requestHandler.getAnswer(curRequest.getRequest(), curUser);
            output.print(curUser.getId(), curAnswer);
        }
    }
}
