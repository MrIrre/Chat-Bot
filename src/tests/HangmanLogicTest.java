package tests;

import chatbot.User;
import enums.Status;
import games.HangmanLogic;
import iomanager.AnswerReader;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static org.junit.Assert.*;
import static tests.RequestHandlerTest.CONSOLE_ID;

public class HangmanLogicTest {

    @Test
    public void startHangman() throws Exception {
        User testUser = createUser();

        ArrayList<String> allWords = AnswerReader.ParseAnswersFromFile("FilesForBot/WordsForHangman.txt");

        var actual = testUser.game.handleGameRequest("2", testUser);
        String curWord = ((HangmanLogic) testUser.game).getCurWord();
        String wordForUser = actual.get(1).substring(12);

        for (int i = 0; i < wordForUser.length(); i++)
            Assert.assertEquals('*', wordForUser.charAt(i));

        Assert.assertTrue(allWords.contains(curWord));
    }

    @Test
    public void rightAnswer() throws Exception {
        User testUser = createUser();
        var indexes = new HashSet<Integer>();

        testUser.game.handleGameRequest("2", testUser);
        String curWord = ((HangmanLogic) testUser.game).getCurWord();
        char userAnswer = curWord.charAt(0);

        for (int i = 1; i < curWord.length(); i++)
            if (userAnswer == curWord.charAt(i))
                indexes.add(i);

        var actual = testUser.game.handleGameRequest(Character.toString(userAnswer), testUser);
        String wordForUser = actual.get(2).substring(12);

        for (int i = 1; i < wordForUser.length(); i++) {
            if (indexes.contains(i))
                Assert.assertEquals(userAnswer, curWord.charAt(i));
            else
                Assert.assertEquals('*', wordForUser.charAt(i));
        }

        Assert.assertEquals(wordForUser.charAt(0), userAnswer);
    }

    @Test
    public void wrongAnswer() throws Exception {
        User testUser = createUser();

        testUser.game.handleGameRequest("2", testUser);
        String curWord = ((HangmanLogic) testUser.game).getCurWord();

        String alphabet = "абвгдеёжзийклмнопрстуфхцчшщьыъэюя";
        char wrongAnswer = alphabet.charAt(new Random().nextInt(33));
        while (curWord.indexOf(wrongAnswer) != -1)
            wrongAnswer = alphabet.charAt(new Random().nextInt(33));

        testUser.game.handleGameRequest(Character.toString(wrongAnswer), testUser);
        String wordForUser = ((HangmanLogic) testUser.game).getWordForUser();

        for (int i = 0; i < wordForUser.length(); i++)
            Assert.assertEquals('*', wordForUser.charAt(i));

        int health = ((HangmanLogic) testUser.game).getHealth();

        Assert.assertSame(9, health);
    }

    @Test
    public void invalidInput() throws Exception {
        User testUser = createUser();

        testUser.game.handleGameRequest("2", testUser);
        var actual = testUser.game.handleGameRequest("abracadabra", testUser);
        Assert.assertEquals("Введи одну букву русского алфавита, пожалуйста)", actual.get(0));
    }

    private User createUser() {
        User testUser = new User(CONSOLE_ID);
        testUser.game = new HangmanLogic();
        testUser.state = Status.Playing;

        return testUser;
    }
}