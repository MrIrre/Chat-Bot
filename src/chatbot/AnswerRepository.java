package chatbot;

import iomanager.AnswerReader;

import java.util.ArrayList;
import java.util.Random;

/**
 * Хранилище возможных ответов бота на какие-либо запросы пользователя(Будет дополняться).
 */
public class AnswerRepository {
    public static ArrayList<String> helloAnswers = AnswerReader.ParseAnswersFromFile("FilesForBot/Answers/Hello.txt");
    public static ArrayList<String> rightAnswers = AnswerReader.ParseAnswersFromFile("FilesForBot/Answers/Right.txt");
    public static ArrayList<String> wrongAnswers = AnswerReader.ParseAnswersFromFile("FilesForBot/Answers/Wrong.txt");

    private static Random random = new Random();

    public static String getRandomAnswer(ArrayList<String> answers){
        return answers.get(random.nextInt(answers.size()));
    }

    public static String getScoreString() {
        return "Количество очков: ";
    }

    public static String getHealthString(){
        return "Ваши HP, сэр: ";
    }

    public static String getWinString(){
        return "Победа! Слава и хвала победителю! :D";
    }

    public static String getLoseString() {
        return "Вы проиграли... Не расстраивайтесь, в следующий раз обязательно получится:)";
    }

    public static String getGameOverString(){
        return "Игра окончена! Спасибо за игру:3 Для начала новой игры напиши \"/start\".";
    }

    public static String getHPEndedString(){
        return "ХП нема. Это конец.";
    }

    public static String getAnswerEndedString() {
        return "Вы ответили на все вопросы! Поздравляю!";
    }

    public static String getWrongRequestAnswerString(){
        return "Сорян, не понял. Давай ещё раз.";
    }

    public static String getExitString(){
        return "Вышел.";
    }

    public static String getWrongStartRequestAnswerString(){
        return "Введи, пожалуйста, /start для начала)";
    }
}
