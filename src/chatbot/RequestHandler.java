package chatbot;

import enums.Status;
import iomanager.QuestionsFromSite;

import java.util.ArrayList;
import java.util.Random;

/**
 * Логика бота. Обработка текущего запроса, в зависимости от состояния пользователя, отправившего данный запрос.
 */
public class RequestHandler {
    private static AnswerRepository answerRepository = new AnswerRepository();
    private static Random random = new Random();

    /**
     * Метод, обрабатывающий запрос, который возвращает ответ на данный запрос.
     */
    public static ArrayList<String> GetAnswer(String inputString, User user) throws Exception {
        ArrayList<String> answerList = new ArrayList<>();

        if (user.State == Status.StartGame) {
            if (!inputString.equals("/start")){
                answerList.add(answerRepository.getWrongRequestAnswerString());
            }
            else{
                answerList.add(answerRepository.getRandomHello());
                user.State = Status.AnswerTheQuestion;
                user.QuestionsAndAnswers =
                        QuestionsFromSite.quizParser(random.nextInt(QuestionsFromSite.NumberOfPages));
                user.AllQuestions = new ArrayList<>(user.QuestionsAndAnswers.keySet());
                user.CurQuestion = user.AllQuestions.get(random.nextInt(user.AllQuestions.size()));
                answerList.add(user.CurQuestion);
            }
        }

        else if (user.State == Status.AnswerTheQuestion){
            if (user.QuestionsAndAnswers.get(user.CurQuestion).contains(inputString.toLowerCase())){
                answerList.add(answerRepository.getRandomRightAnswer());
                user.scoreUp();
            }
            else {
                answerList.add(answerRepository.getRandomWrongAnswer());
                user.healthDown();
            }

            user.AllQuestions.remove(user.CurQuestion);
            user.QuestionsAndAnswers.remove(user.CurQuestion);

            answerList.add(answerRepository.getScoreString() + user.getScore());
            answerList.add(answerRepository.getHealthString() + user.getHealth());

            if (user.getHealth() <= 0){
                user.changeWinState();
                user.State = Status.GameOver;
                answerList.add(answerRepository.getHPEndedString());
            }
            else if (user.AllQuestions.isEmpty()){
                user.State = Status.GameOver;
                answerList.add(answerRepository.getAnswerEndedString());
            }

            if (user.State == Status.GameOver){
                if (user.isWin()){
                    answerList.add(answerRepository.getWinString());
                }
                else{
                    answerList.add(answerRepository.getLoseString());
                }

                user.State = Status.StartGame;
                user.resetUser();
                answerList.add(answerRepository.getGameOverString());
            }
            else{
                user.CurQuestion = user.AllQuestions.get(random.nextInt(user.AllQuestions.size()));
                answerList.add("==============================================");
                answerList.add(user.CurQuestion);
            }
        }

        return answerList;
    }
}
