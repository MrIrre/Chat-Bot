package games;

import chatbot.AnswerRepository;
import chatbot.User;
import enums.Status;
import interfaces.Game;
import iomanager.QuestionsFromSite;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class QuizLogic implements Game {
    private final Random random = new Random();
    private int score = 0;
    private int health = 3;
    private boolean winState = true;
    private ArrayList<String> answerList;

    private Map<String, Set<String>> questionsAndAnswers; // Вопросы и множество ответов на каждый их них
    private ArrayList<String> allQuestions; // Лист для рандомизации текущего вопроса
    private String curQuestion;

    //Все геттеры, находящиеся ниже нужны для тестирования
    public Map<String, Set<String>> getQuestionsAndAnswers() {
        return questionsAndAnswers;
    }

    public String getCurQuestion() {
        return curQuestion;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    @Override
    public ArrayList<String> handleGameRequest(String inputString, User user) throws Exception {
        answerList = new ArrayList<>();

        if (questionsAndAnswers == null){
            giveQuestions();
            answerList.add(curQuestion);
        }
        else{
            /*
             Проверка ответа от пользователя.
             Изменение количества очков, жизней у пользователя.
             Проверка на проигрыш.
            */
            answerList = checkAnswer(inputString);

            answerList.add(AnswerRepository.getScoreString() + score);
            answerList.add(AnswerRepository.getHealthString() + health);

            if (health <= 0){
                winState = false;
                user.state = Status.GameOver;
                answerList.add(AnswerRepository.getHPEndedString());
            }
            else if (allQuestions.isEmpty()){
                user.state = Status.GameOver;
                answerList.add(AnswerRepository.getAnswerEndedString());
            }

            if (user.state == Status.GameOver){
                if (winState){
                    answerList.add(AnswerRepository.getWinString());
                }
                else{
                    answerList.add(AnswerRepository.getLoseString());
                }

                user.state = Status.Starting;
                answerList.add(AnswerRepository.getGameOverString());
            }
            else{
                curQuestion = allQuestions.get(random.nextInt(allQuestions.size()));
                answerList.add("");
                answerList.add(curQuestion);
            }
        }

        return answerList;
    }

    /**
     * Метод проверяет является ли ответ на вопрос правильным и удаляет вопрос из списка вопросов.
     * @param inputtedAnswer Введённый ответ на вопрос
     * @return Лист ответа со строками, которые зависят от правильности ответа
     */
    private ArrayList<String> checkAnswer(String inputtedAnswer) {
        if (questionsAndAnswers.get(curQuestion).contains(inputtedAnswer.toLowerCase())){
            answerList.add(AnswerRepository.getRandomAnswer(AnswerRepository.rightAnswers));
            score++;
        }
        else {
            answerList.add(AnswerRepository.getRandomAnswer(AnswerRepository.wrongAnswers));
            health--;
        }

        allQuestions.remove(curQuestion);
        questionsAndAnswers.remove(curQuestion);

        return answerList;
    }

    /**
     * Метод, выдающий список вопросов, ответов и первый вопрос в данном сеансе игры.
     */
    private void giveQuestions() throws Exception {
        questionsAndAnswers =
                QuestionsFromSite.quizParser(random.nextInt(QuestionsFromSite.NUMBER_OF_PAGES));
        allQuestions = new ArrayList<>(questionsAndAnswers.keySet());
        curQuestion = allQuestions.get(random.nextInt(allQuestions.size()));
    }
}
