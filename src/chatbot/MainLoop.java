package chatbot;

import enums.RequestFrom;
import interfaces.InputOutput;

import java.util.*;

/**
 * Главный цикл чат-бота, который на каждой итерации обрабатывает запрос пользователя.
 */
public class MainLoop {
    private Map<String, User> users = Collections.synchronizedMap(new HashMap<>());
    private Deque<Request> requests = new ArrayDeque<>();
    private RequestHandler requestHandler = new RequestHandler();

    private Request inputtedRequest;
    private InputOutput curVersionOutput = null;

    /**
     * Запуск главного цикла.
     * @param versions Map, в котором лежат классы, реализующие InputOutput интерфейс.
     */
    void runMainLoop(Map<RequestFrom, InputOutput> versions) throws Exception {

        while (true){
            Thread.sleep(10);

            for(RequestFrom curVersion: versions.keySet()){
                inputtedRequest = versions.get(curVersion).getRequest();

                if (inputtedRequest != null && !inputtedRequest.getRequest().equals("")){
                    requests.push(inputtedRequest);
                }
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

            curVersionOutput = versions.get(curRequest.getRequestFrom());
            curVersionOutput.print(curUser.getId(), curAnswer);
        }
    }
}
