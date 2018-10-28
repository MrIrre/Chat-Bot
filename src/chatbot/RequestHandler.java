package chatbot;

import enums.Status;
import games.HangmanLogic;
import games.QuizLogic;

import java.util.ArrayList;

/**
 * Логика бота. Обработка текущего запроса, в зависимости от состояния пользователя, отправившего данный запрос.
 */
public class RequestHandler {
    public static final String EXIT_STRING = "/exit";

    /**
     * Метод, обрабатывающий запрос, который возвращает ответ на данный запрос.
     * @param inputString Строка запроса.
     * @param user Пользователь, запрос которого, обрабатывается.
     * @return Строки с ответом.
     */
    public ArrayList<String> getAnswer(String inputString, User user) throws Exception {
        ArrayList<String> answerList = new ArrayList<>();

        /*
        Если пользователь только начал игру, то выдаётся строка приветствия.
        Также меняется состояние пользователя на игровое.
         */

        if (user.state == Status.Starting) {
            if (!inputString.equals("/start")){
                answerList.add(AnswerRepository.getWrongStartRequestAnswerString());
            }
            else{
                answerList.add(AnswerRepository.getRandomAnswer(AnswerRepository.helloAnswers));
                user.state = Status.ChoosingGame;
                answerList.add("Чтобы выйти в любой момент из игры напиши \"" + EXIT_STRING + "\"");
                answerList.add(AnswerRepository.getGamesString());
            }
        }

        else if (user.state == Status.ChoosingGame){
            switch (inputString) {
                case EXIT_STRING:
                    user.state = Status.Starting;
                    answerList.add(AnswerRepository.getExitString());
                    break;
                case "1":
                    user.game = new QuizLogic();
                    user.state = Status.Playing;
                    break;
                case "2":
                    user.game = new HangmanLogic();
                    user.state = Status.Playing;
                    break;
                default:
                    answerList.add(AnswerRepository.getWrongRequestAnswerString());
                    break;
            }
        }

        /*
         * Если пользователь играет, то его запросы обрабатывает определённая игра.
         */
        if (user.state == Status.Playing){
            if (inputString.equals(EXIT_STRING)){
                user.game = null;
                user.state = Status.Starting;
                answerList.add(AnswerRepository.getExitString());
            }
            else{
                answerList.addAll(user.game.handleGameRequest(inputString, user));
            }
        }

        return answerList;
    }
}
