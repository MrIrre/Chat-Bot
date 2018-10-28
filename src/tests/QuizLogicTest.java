package tests;

import chatbot.AnswerRepository;
import chatbot.User;
import enums.Status;
import games.QuizLogic;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static tests.RequestHandlerTest.CONSOLE_ID;

public class QuizLogicTest {

    @Test
    public void startGame() throws Exception {
        User testUser = createUser();
        var actual = testUser.game.handleGameRequest("1", testUser);
        Assert.assertSame(1, actual.size());
    }

    @Test
    public void rightAnswer() throws Exception {
        User testUser = createUser();
        testUser.game.handleGameRequest("1", testUser);
        var curQuestion = ((QuizLogic) testUser.game).getCurQuestion();
        var questionAndAnswer = ((QuizLogic) testUser.game).getQuestionsAndAnswers();
        var rightAnswers = AnswerRepository.rightAnswers;
        var actual = testUser.game.handleGameRequest(questionAndAnswer.get(curQuestion).iterator().next(), testUser);
        Assert.assertTrue(rightAnswers.contains(actual.get(0))
                || ((QuizLogic) testUser.game).getScore() == 1);
    }

    @Test
    public void wrongAnswer() throws Exception {
        User testUser = createUser();
        testUser.game.handleGameRequest("1", testUser);
        var wrongAnswers = AnswerRepository.wrongAnswers;
        var actual = testUser.game.handleGameRequest("111", testUser);
        Assert.assertTrue(wrongAnswers.contains(actual.get(0))
                || ((QuizLogic) testUser.game).getHealth() == 2);
    }

    @Test
    public void healthEnded() throws Exception {
        User testUser = createUser();
        testUser.game.handleGameRequest("1", testUser);

        for (int i = 0; i < 2; i++)
            testUser.game.handleGameRequest("111", testUser);

        var actual = testUser.game.handleGameRequest("111", testUser);

        Assert.assertSame(testUser.state, Status.Starting);
        Assert.assertEquals(AnswerRepository.getHPEndedString(), actual.get(3));
        Assert.assertEquals(AnswerRepository.getLoseString(), actual. get(4));
    }

    private User createUser(){
        User testUser = new User(CONSOLE_ID);
        testUser.state = Status.Playing;
        testUser.game = new QuizLogic();
        return testUser;
    }
}