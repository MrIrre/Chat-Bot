package chatbot;

import iomanager.AnswerReader;

import java.util.ArrayList;
import java.util.Random;

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

    String getRandomHello(){
        return HelloAnswers.get(random.nextInt(HelloAnswers.size()));
    }

    String getRandomRightAnswer(){
        return RightAnswers.get(random.nextInt(RightAnswers.size()));
    }

    String getRandomWrongAnswer(){
        return WrongAnswers.get(random.nextInt(WrongAnswers.size()));
    }

    String getScoreString() {
        return "Количество очков: ";
    }

    String getHealthString(){
        return "Ваши HP, сэр: ";
    }

    String getGameOverString(){
        return "Игра окончена! Спасибо за игру:3";
    }

    String getHPEndedString(){
        return "ХП нема. Это конец.";
    }

    String getAnswerEndedString() {
        return "Вы ответили на все вопросы! Поздравляю!";
    }

    String getWrongRequestAnswerString(){
        return "Сорян, не понял. Давай ещё раз.";
    }
}
