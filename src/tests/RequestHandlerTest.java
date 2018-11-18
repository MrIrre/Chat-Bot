package tests;

import chatbot.*;
import enums.*;
import games.HangmanLogic;
import games.QuizLogic;
import iomanager.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static chatbot.RequestHandler.EXIT_STRING;

public class RequestHandlerTest {
    private RequestHandler requestHandler = new RequestHandler();
    static final String CONSOLE_ID = "ConsoleUser";

    @Test
    public void correctStartCase() throws Exception {
        ArrayList<String> actual;

        User testUser1 = createUser();
        User testUser2 = new User(CONSOLE_ID);

        String testInputtedString = "/start";
        ArrayList<String> helloAnswers = AnswerReader.ParseAnswersFromFile("FilesForBot/Answers/Hello.txt");
        String gamesString = "Выбирай игру: \n" +
                "1. Викторина; \n" +
                "2. Виселица;";

        actual = requestHandler.getAnswer(testInputtedString, testUser2);

        Assert.assertTrue(gamesString.equals(actual.get(2))
                && helloAnswers.contains(actual.get(0))
                && actual.get(1).equals("Чтобы выйти в любой момент из игры напиши \"" + EXIT_STRING + "\"")
                && testUser1.state == testUser2.state);
    }

    @Test
    public void incorrectStartCase() throws Exception {
        ArrayList<String> actual;

        User testUser1 = new User(CONSOLE_ID);
        User testUser2 = new User(CONSOLE_ID);

        String testInputtedString = "abracadabra";
        String wrongRequestAnswer = AnswerRepository.getWrongStartRequestAnswerString();

        actual = requestHandler.getAnswer(testInputtedString, testUser2);

        Assert.assertEquals(wrongRequestAnswer, actual.get(0));
        Assert.assertSame(testUser1.state, testUser2.state);
    }

    @Test
    public void choosingGameCase() throws Exception {
        User testUserQuiz = createUser();
        User testUserHangman = createUser();

        requestHandler.getAnswer("1", testUserQuiz);
        requestHandler.getAnswer("2", testUserHangman);

        Assert.assertSame(testUserQuiz.state, Status.Playing);
        Assert.assertSame(testUserHangman.state, Status.Playing);
    }

    @Test
    public void playingQuizCase() throws Exception{
        User testUser1 = createUser();
        testUser1.game = new QuizLogic();
        testUser1.state = Status.Playing;

        requestHandler.getAnswer("Ответ", testUser1);

        Assert.assertSame(testUser1.state, Status.Playing);
    }

    @Test
    public void playingHangmanCase() throws Exception{
        User testUser1 = createUser();
        testUser1.game = new HangmanLogic();
        testUser1.state = Status.Playing;

        requestHandler.getAnswer("Ответ", testUser1);

        Assert.assertSame(testUser1.state, Status.Playing);
    }

    private User createUser() throws Exception {
        User createdUser = new User(CONSOLE_ID);
        createdUser.state = Status.ChoosingGame;
        return createdUser;
    }
}