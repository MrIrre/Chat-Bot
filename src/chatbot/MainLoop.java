package chatbot;

import enums.Version;
import interfaces.Input;
import interfaces.Output;

import java.util.*;

/**
 * Главный цикл чат-бота, который на каждой итерации обрабатывает запрос пользователя.
 */
public class MainLoop {
    private static Map<String, User> Users = new HashMap<>();
    private static Deque<Request> Requests = new ArrayDeque<>();

    public void runMainLoop(Input input, Output output) throws Exception {

        while (true){
            Request inputedRequest = input.GetRequest();

            if (!inputedRequest.getRequest().equals("")){
                Requests.push(inputedRequest);
            }

            if (Requests.isEmpty()){
                continue;
            }

            Request curRequest = Requests.pop();

            if (!Users.containsKey(curRequest.getUserId())){
                Version curUserVersion = curRequest.getVersion();
                Users.put(curRequest.getUserId(), new User(curRequest.getUserId(), curUserVersion));
            }

            User curUser = Users.get(curRequest.getUserId());
            ArrayList<String> curAnswer = RequestHandler.GetAnswer(curRequest.getRequest(), curUser);
            output.Print(curAnswer);
        }
    }
}
