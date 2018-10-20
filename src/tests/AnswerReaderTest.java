package tests;

import iomanager.AnswerReader;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AnswerReaderTest {

    @Test
    public void parseAnswersFromFile() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Здравствуй, друг!");
        expected.add("Приветствую, незнакомец!");
        expected.add("Конничива!");
        expected.add("Хай, мой друг!");
        expected.add("Салют, мсье!");

        ArrayList<String> actual = AnswerReader.ParseAnswersFromFile("Answers/Hello.txt");

        Assert.assertThat(expected, is(actual));
    }
}