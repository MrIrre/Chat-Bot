package chatbot;

import enums.Status;
import iomanager.QuestionsFromSite;

import java.util.ArrayList;
import java.util.Random;

/**
 * Логика бота. Обработка текущего запроса, в зависимости от состояния пользователя, отправившего данный запрос.
 */
public class RequestHandler {
    private final String delimeterBetweenQuestions = "==============================================";
    private final Random random = new Random();

    /**
     * Метод, обрабатывающий запрос, который возвращает ответ на данный запрос.
     * @param inputString Строка запроса.
     * @param user Пользователь, запрос которого, обрабатывается.
     * @return Строки с ответом.
     */
    public ArrayList<String> getAnswer(String inputString, User user) throws Exception {
        ArrayList<String> answerList = new ArrayList<>();

        /*
        Если пользователь только начал игру, то выдаётся строка приветствия, и его классу выдаются вопросы.
        Также меняется состояние пользователя на игровое.
         */
        if (user.state == Status.StartGame) {
            if (!inputString.equals("/start")){
                answerList.add(AnswerRepository.getWrongRequestAnswerString());
            }
            else{
                answerList.add(AnswerRepository.getRandomHello());
                user.state = Status.AnswerTheQuestion;
                user.questionsAndAnswers =
                        QuestionsFromSite.quizParser(random.nextInt(QuestionsFromSite.NUMBER_OF_PAGES));
                user.allQuestions = new ArrayList<>(user.questionsAndAnswers.keySet());
                user.curQuestion = user.allQuestions.get(random.nextInt(user.allQuestions.size()));
                answerList.add(user.curQuestion);
            }
        }

        /*
        Проверка ответа от пользователя.
        Изменение количества очков, жизней у пользователя.
        Проверка на проигрыш.
         */
        else if (user.state == Status.AnswerTheQuestion){
            answerList = checkAnswer(user, answerList, inputString);

            answerList.add(AnswerRepository.getScoreString() + user.getScore());
            answerList.add(AnswerRepository.getHealthString() + user.getHealth());

            if (user.getHealth() <= 0){
                user.changeWinState();
                user.state = Status.GameOver;
                answerList.add(AnswerRepository.getHPEndedString());
            }
            else if (user.allQuestions.isEmpty()){
                user.state = Status.GameOver;
                answerList.add(AnswerRepository.getAnswerEndedString());
            }

            if (user.state == Status.GameOver){
                if (user.isWin()){
                    answerList.add(AnswerRepository.getWinString());
                }
                else{
                    answerList.add(AnswerRepository.getLoseString());
                }

                user.state = Status.StartGame;
                user.resetUser();
                answerList.add(AnswerRepository.getGameOverString());
            }
            else{
                user.curQuestion = user.allQuestions.get(random.nextInt(user.allQuestions.size()));
                answerList.add(delimeterBetweenQuestions);
                answerList.add(user.curQuestion);
            }
        }

        return answerList;
    }

    /**
     * Метод проверяет является ли ответ на вопрос правильным и удаляет вопрос из списка вопросов пользователя.
     * @param user Данный пользователь
     * @param answerList Лист строк в ответ на данный запрос
     * @param inputedAnswer Введённый ответ на вопрос
     * @return Лист ответа со строками, которые зависят от правильности ответа
     */
    ArrayList<String> checkAnswer(User user, ArrayList<String> answerList, String inputedAnswer) {
        if (user.questionsAndAnswers.get(user.curQuestion).contains(inputedAnswer.toLowerCase())){
            answerList.add(AnswerRepository.getRandomRightAnswer());
            user.scoreUp();
        }
        else {
            answerList.add(AnswerRepository.getRandomWrongAnswer());
            user.healthDown();
        }

        user.allQuestions.remove(user.curQuestion);
        user.questionsAndAnswers.remove(user.curQuestion);

        return answerList;
    }
}
