package tests;

import iomanager.QuestionsReader;
import org.junit.Assert;
import org.junit.Test;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.core.Is.is;


public class QuestionsReaderTest {
    private String TestFilePath = "Tests/TestsForQuestionsReader.txt";

    @Test
    public void getDataFromFile() {
        Map<String, Set<String>> expectedMap = new HashMap<>();
        Set<String> firstStringAnswers = new HashSet<>();

        firstStringAnswers.add("987654321");

        expectedMap.put("123456789", firstStringAnswers);

        Set<String> secondStringAnswers = new HashSet<>();
        secondStringAnswers.add("Саша");
        secondStringAnswers.add("Паша");
        secondStringAnswers.add("Алёша");
        secondStringAnswers.add("Сергей");
        secondStringAnswers.add("Миша");

        expectedMap.put("Какие есть имена?", secondStringAnswers);

        Map<String, Set<String>> actualMap =
                QuestionsReader.GetDataFromFile(TestFilePath);

        Assert.assertThat(expectedMap, is(actualMap));
    }
}
