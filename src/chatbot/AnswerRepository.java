package chatbot;

import iomanager.AnswerReader;

import java.util.ArrayList;
import java.util.Random;

/**
 * Хранилище возможных ответов бота на какие-либо запросы пользователя(Будет дополняться).
 */
class AnswerRepository {
    private ArrayList<String> HelloAnswers;
    private ArrayList<String> RightAnswers;
    private ArrayList<String> WrongAnswers;

    private static Random random = new Random();


    AnswerRepository(){
        HelloAnswers = AnswerReader.ParseAnswersFromFile("Answers/Hello.txt");
        RightAnswers = AnswerReader.ParseAnswersFromFile("Answers/Right.txt");
        WrongAnswers = AnswerReader.ParseAnswersFromFile("Answers/Wrong.txt");
    }

    public String getRandomHello(){
        return HelloAnswers.get(random.nextInt(HelloAnswers.size()));
    }

    public String getRandomRightAnswer(){
        return RightAnswers.get(random.nextInt(RightAnswers.size()));
    }

    public String getRandomWrongAnswer(){
        return WrongAnswers.get(random.nextInt(WrongAnswers.size()));
    }

    public String getScoreString() {
        return "Количество очков: ";
    }

    public String getHealthString(){
        return "Ваши HP, сэр: ";
    }

    public String getWinString(){
        return "Победа! Слава и хвала победителю! :D";
    }

    public String getLoseString() {
        return "Вы проиграли... Не расстраивайтесь, в следующий раз обязательно получится:)";
    }

    public String getGameOverString(){
        return "Игра окончена! Спасибо за игру:3 Для начала новой игры напиши \"/start\".";
    }

    public String getHPEndedString(){
        return "ХП нема. Это конец.";
    }

    public String getAnswerEndedString() {
        return "Вы ответили на все вопросы! Поздравляю!";
    }

    public String getWrongRequestAnswerString(){
        return "Сорян, не понял. Давай ещё раз.";
    }
}
