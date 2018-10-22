package tests;

import chatbot.*;
import enums.*;
import iomanager.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class RequestHandlerTest {
    public RequestHandler requestHandler = new RequestHandler();
    private final String CONSOLE_ID = "ConsoleUser";

    @Test
    public void correctStartCase() throws Exception {
        ArrayList<String> actual;

        User testUser1 = createUser();
        User testUser2 = new User(CONSOLE_ID, Version.Console);

        String testInputedString = "/start";
        ArrayList<String> helloAnswers = AnswerReader.ParseAnswersFromFile("Answers/Hello.txt");

        actual = requestHandler.getAnswer(testInputedString, testUser2);

        Assert.assertTrue(helloAnswers.contains(actual.get(0))
                || testUser1.allQuestions.contains(actual.get(1)));
    }

    @Test
    public void incorrectStartCase() throws Exception {
        ArrayList<String> actual;

        User testUser1 = new User("ConsoleUser", Version.Console);
        User testUser2 = new User("ConsoleUser", Version.Console);

        String testInputedString = "abracadabra";
        String wrongRequestAnswer = AnswerRepository.getWrongRequestAnswerString();

        actual = requestHandler.getAnswer(testInputedString, testUser2);

        Assert.assertEquals(wrongRequestAnswer, actual.get(0));
        Assert.assertSame(testUser1.state, testUser2.state);
        Assert.assertSame(testUser1.getHealth(), testUser2.getHealth());
        Assert.assertSame(testUser1.getScore(), testUser2.getScore());
    }

    @Test
    public void rightAnswerCase() throws Exception {
        User testUser = createUser();

        testUser.curQuestion = testUser.allQuestions.get(0);
        ArrayList<String> rightAnswers = AnswerReader.ParseAnswersFromFile("Answers/Right.txt");

        String rightAnswer = new ArrayList<>(testUser.questionsAndAnswers.get(testUser.curQuestion)).get(0);
        var actualRight = requestHandler.getAnswer(rightAnswer, testUser);

        Assert.assertTrue(rightAnswers.contains(actualRight.get(0)));
    }

    @Test
    public void wrongAnswerCase() throws Exception{
        User testUser1 = createUser();

        testUser1.curQuestion = testUser1.allQuestions.get(0);
        ArrayList<String> rightAnswers = AnswerReader.ParseAnswersFromFile("Answers/Right.txt");

        var actualRight = requestHandler.getAnswer("неверныйОтвет", testUser1);

        Assert.assertFalse(rightAnswers.contains(actualRight.get(0)));
    }

    private User createUser() throws Exception {
        User createdUser = new User("ConsoleUser", Version.Console);
        createdUser.state = Status.AnswerTheQuestion;
        createdUser.questionsAndAnswers = QuestionsFromSite.quizParser(0);
        createdUser.allQuestions = new ArrayList<>(createdUser.questionsAndAnswers.keySet());
        return createdUser;
    }
}