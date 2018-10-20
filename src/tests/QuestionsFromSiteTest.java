package tests;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import iomanager.*;

import static org.hamcrest.CoreMatchers.is;

public class QuestionsFromSiteTest {

    @Test
    public void quizParser() throws Exception {
        var answerSet = new HashSet<String>();
        var expected = new HashMap<String, Set<String>>();

        answerSet.add("бильярд");
        expected.put("В какую из этих игр играют не клюшкой?", new HashSet<>(answerSet));
        answerSet.clear();

        answerSet.add("берлин");
        expected.put("В каком городе не работал великий композитор 18-го века Кристоф Виллибальд Глюк?", new HashSet<>(answerSet));
        answerSet.clear();

        answerSet.add("галлей");
        expected.put("Кто первым доказал периодичность появления комет?", new HashSet<>(answerSet));
        answerSet.clear();

        answerSet.add("сухая ясная");
        expected.put("Про какую летнюю погоду говорят Вёдро ?", new HashSet<>(answerSet));
        answerSet.clear();

        answerSet.add("венгрия");
        expected.put("С какой из этих стран Чехия не граничит?", new HashSet<>(answerSet));
        answerSet.clear();

        answerSet.add("дагестан");
        expected.put("Где в основном проживают таты?", new HashSet<>(answerSet));
        answerSet.clear();

        answerSet.add("сокол");
        expected.put("Как, в переводе на русский язык, звучало бы название фильма 'Мимино'?", new HashSet<>(answerSet));
        answerSet.clear();

        answerSet.add("фордевинд");
        expected.put("Как называется курс парусного судна, совпадающий с направлением ветра?", new HashSet<>(answerSet));
        answerSet.clear();

        answerSet.add("корковадо");
        expected.put("На вершине какой горы расположена сорокаметровая статуя Христа, являющаяся символом Рио-де-Жанейро?", new HashSet<>(answerSet));
        answerSet.clear();

        answerSet.add("сытое");
        expected.put("Какое брюхо, согласно спорной русской пословице, глухо к ученью?", new HashSet<>(answerSet));
        answerSet.clear();

        var actualTemp = QuestionsFromSite.quizParser(0);
        Map<String, Set<String>> actual = new HashMap<>();

        for (String curStringActual:actualTemp.keySet()) {
            actual.put(curStringActual.substring(0, curStringActual.indexOf("[")), actualTemp.get(curStringActual));
        }

        Assert.assertThat(expected, is(actual));
    }

    @Test
    public void wrongNumberOfPage() throws Exception {
        try {
            QuestionsFromSite.quizParser(400);
        } catch (UnknownHostException | FileNotFoundException noPageOrNoNet) {
            Assert.assertNotEquals("", noPageOrNoNet.getMessage());
        }
    }
}