package chatbot;

import enums.Status;
import games.HangmanLogic;
import games.QuizLogic;

import java.util.ArrayList;

/**
 * Логика бота. Обработка текущего запроса, в зависимости от состояния пользователя, отправившего данный запрос.
 */
public class RequestHandler {
    /**
     * Метод, обрабатывающий запрос, который возвращает ответ на данный запрос.
     * @param inputString Строка запроса.
     * @param user Пользователь, запрос которого, обрабатывается.
     * @return Строки с ответом.
     */
    ArrayList<String> getAnswer(String inputString, User user) throws Exception {
        ArrayList<String> answerList = new ArrayList<>();

        /*
        Если пользователь только начал игру, то выдаётся строка приветствия.
        Также меняется состояние пользователя на игровое.
         */

        if (user.state == Status.Starting) {
            if (!inputString.equals("/start")){
                answerList.add(AnswerRepository.getWrongRequestAnswerString());
            }
            else{
                answerList.add(AnswerRepository.getRandomAnswer(AnswerRepository.helloAnswers));
                user.state = Status.ChoosingGame;
                answerList.add(AnswerRepository.getGamesString());
            }
        }

        else if (user.state == Status.ChoosingGame){
            if (inputString.equals("1")){
                user.game = new QuizLogic();
                user.state = Status.Playing;
            }
            else if (inputString.equals("2")){
                user.game = new HangmanLogic();
                user.state = Status.Playing;
            }
            else {
                answerList.add(AnswerRepository.getWrongRequestAnswerString());
            }
        }

        /*
         * Если пользователь играет, то его запросы обрабатывает определённая игра.
         */
        if (user.state == Status.Playing){
            answerList.addAll(user.game.handleGameRequest(inputString, user));
        }

        return answerList;
    }
}
