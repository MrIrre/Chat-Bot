package tests;

import chatbot.*;
import enums.*;
import iomanager.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class RequestHandlerTest {
    public RequestHandler RequestHandler = new RequestHandler();

    @Test
    public void correctStartCase() throws Exception {
        ArrayList<String> actual;

        User testUser1 = createUser();
        User testUser2 = new User("ConsoleUser", Version.Console);

        String testInputedString = "/start";
        ArrayList<String> helloAnswers = AnswerReader.ParseAnswersFromFile("Answers/Hello.txt");

        actual = RequestHandler.getAnswer(testInputedString, testUser2);

        Assert.assertTrue(helloAnswers.contains(actual.get(0))
                || testUser1.AllQuestions.contains(actual.get(1)));
    }

    @Test
    public void incorrectStartCase() throws Exception {
        ArrayList<String> actual;

        User testUser1 = new User("ConsoleUser", Version.Console);
        User testUser2 = new User("ConsoleUser", Version.Console);

        String testInputedString = "abracadabra";
        String wrongRequestAnswer = AnswerRepository.getWrongRequestAnswerString();

        actual = RequestHandler.getAnswer(testInputedString, testUser2);

        Assert.assertEquals(wrongRequestAnswer, actual.get(0));
        Assert.assertSame(testUser1.State, testUser2.State);
        Assert.assertSame(testUser1.getHealth(), testUser2.getHealth());
        Assert.assertSame(testUser1.getScore(), testUser2.getScore());
    }

    @Test
    public void rightAnswerCase() throws Exception {
        User testUser = createUser();

        testUser.CurQuestion = testUser.AllQuestions.get(0);
        ArrayList<String> rightAnswers = AnswerReader.ParseAnswersFromFile("Answers/Right.txt");

        String rightAnswer = new ArrayList<>(testUser.QuestionsAndAnswers.get(testUser.CurQuestion)).get(0);
        var actualRight = RequestHandler.getAnswer(rightAnswer, testUser);

        Assert.assertTrue(rightAnswers.contains(actualRight.get(0)));
    }

    @Test
    public void wrongAnswerCase() throws Exception{
        User testUser1 = createUser();

        testUser1.CurQuestion = testUser1.AllQuestions.get(0);
        ArrayList<String> rightAnswers = AnswerReader.ParseAnswersFromFile("Answers/Right.txt");

        var actualRight = RequestHandler.getAnswer("неверныйОтвет", testUser1);

        Assert.assertFalse(rightAnswers.contains(actualRight.get(0)));
    }

    private User createUser() throws Exception {
        User createdUser = new User("ConsoleUser", Version.Console);
        createdUser.State = Status.AnswerTheQuestion;
        createdUser.QuestionsAndAnswers = QuestionsFromSite.quizParser(0);
        createdUser.AllQuestions = new ArrayList<>(createdUser.QuestionsAndAnswers.keySet());
        return createdUser;
    }
}