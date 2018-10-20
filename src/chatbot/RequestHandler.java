package chatbot;

import enums.Status;
import iomanager.QuestionsFromSite;

import java.util.ArrayList;
import java.util.Random;

/**
 * Логика бота. Обработка текущего запроса, в зависимости от состояния пользователя, отправившего данный запрос.
 */
public class RequestHandler {
    private final String DelimeterBetweenQuestions = "==============================================";
    private final Random Random = new Random();

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
        if (user.State == Status.StartGame) {
            if (!inputString.equals("/start")){
                answerList.add(AnswerRepository.getWrongRequestAnswerString());
            }
            else{
                answerList.add(AnswerRepository.getRandomHello());
                user.State = Status.AnswerTheQuestion;
                user.QuestionsAndAnswers =
                        QuestionsFromSite.quizParser(Random.nextInt(QuestionsFromSite.NumberOfPages));
                user.AllQuestions = new ArrayList<>(user.QuestionsAndAnswers.keySet());
                user.CurQuestion = user.AllQuestions.get(Random.nextInt(user.AllQuestions.size()));
                answerList.add(user.CurQuestion);
            }
        }

        /*
        Проверка ответа от пользователя.
        Изменение количества очков, жизней у пользователя.
        Проверка на проигрыш.
         */
        else if (user.State == Status.AnswerTheQuestion){
            answerList = checkAnswer(user, answerList, inputString);

            answerList.add(AnswerRepository.getScoreString() + user.getScore());
            answerList.add(AnswerRepository.getHealthString() + user.getHealth());

            if (user.getHealth() <= 0){
                user.changeWinState();
                user.State = Status.GameOver;
                answerList.add(AnswerRepository.getHPEndedString());
            }
            else if (user.AllQuestions.isEmpty()){
                user.State = Status.GameOver;
                answerList.add(AnswerRepository.getAnswerEndedString());
            }

            if (user.State == Status.GameOver){
                if (user.isWin()){
                    answerList.add(AnswerRepository.getWinString());
                }
                else{
                    answerList.add(AnswerRepository.getLoseString());
                }

                user.State = Status.StartGame;
                user.resetUser();
                answerList.add(AnswerRepository.getGameOverString());
            }
            else{
                user.CurQuestion = user.AllQuestions.get(Random.nextInt(user.AllQuestions.size()));
                answerList.add(DelimeterBetweenQuestions);
                answerList.add(user.CurQuestion);
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
        if (user.QuestionsAndAnswers.get(user.CurQuestion).contains(inputedAnswer.toLowerCase())){
            answerList.add(AnswerRepository.getRandomRightAnswer());
            user.scoreUp();
        }
        else {
            answerList.add(AnswerRepository.getRandomWrongAnswer());
            user.healthDown();
        }

        user.AllQuestions.remove(user.CurQuestion);
        user.QuestionsAndAnswers.remove(user.CurQuestion);

        return answerList;
    }
}
