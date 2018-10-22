package iomanager;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Класс отвечает за парсинг веб-страниц с вопросами для викторины
 */
public class QuestionsFromSite {
    public static final int NUMBER_OF_PAGES = 299; //Количество страниц на сайте, откуда берутся вопросы

    /**
     * Функция, которая берет с веб-страницы с данным номером все вопросы и варианты ответов на каждый вопрос.
     * @param pageNumber - номер страницы
     * @return Map, в котором лежат вопросы и отсветы на них.
     * @throws Exception
     */
    public static Map<String, Set<String>> quizParser(int pageNumber) throws Exception {

        Map<String, Set<String>> questionAndAnswer = new HashMap<>();
        URL quizSite;
        if (pageNumber == 0)
            quizSite = new URL("https://baza-otvetov.ru/categories/view/1/");
        else
            quizSite = new URL("https://baza-otvetov.ru/categories/view/1/" + pageNumber + "0");

        BufferedReader in = new BufferedReader(new InputStreamReader(quizSite.openStream()));

        String inputLine;
        StringBuilder quizSiteText = new StringBuilder();

        while ((inputLine = in.readLine()) != null)
            quizSiteText.append(inputLine);

        in.close();

        String quizText = quizSiteText.toString();
        String categoriesFromSite = quizText.substring(quizText.indexOf("q-list__table") + "q-list__table".length() + 2);
        String categories = categoriesFromSite.substring(0, categoriesFromSite.indexOf("</table>"));

        //Меняем все html кавычки на обычные.
        Pattern quotPattern = Pattern.compile("&quot;");
        Matcher quotMatcher = quotPattern.matcher(categories);
        categories = quotMatcher.replaceAll("");

        //Ищем вопросы.
        Pattern questionPattern = Pattern.compile("<a href.+?<\\/a>");
        Matcher questionMatcher = questionPattern.matcher(categories);

        //Ищем варианты ответов.
        Pattern variantsPattern = Pattern.compile("quiz-answers.+?<");
        Matcher variantsMatcher = variantsPattern.matcher(categories);

        ArrayList<String> answers = parseAnswers(categories);

        int counter = 0;

        //Идем по всем найденным вопросам и ответам и записываем их в Map
        while (questionMatcher.find() && variantsMatcher.find() && counter < answers.size()) {
            String curLine = questionMatcher.group();
            String curVariants = variantsMatcher.group();

            String quest = curLine.substring(curLine.indexOf(">") + 1, curLine.lastIndexOf("<"));
            String variants = curVariants.substring(curVariants.indexOf(":") + 2, curVariants.lastIndexOf("<"));

            String curAnswer = answers.get(counter);
            ArrayList<String> variantsList = createVariantsList(variants, curAnswer);

            Set<String> answerSet = new HashSet<>();
            String rightAnsNumber = Integer.toString(variantsList.indexOf(curAnswer) + 1);
            answerSet.add(rightAnsNumber);
            questionAndAnswer.put(quest + addNumbers(variantsList), answerSet);
            counter++;
        }

        return questionAndAnswer;
    }

    /**
     * Функция отвечает за создание списка вариантов ответа на данный вопрос.
     * @param variants - все варианты кроме правильного.
     * @param curAnswer - правильный вариант.
     * @return Список вариантов ответа.
     */
    private static ArrayList<String> createVariantsList(String variants, String curAnswer) {
        ArrayList<String> resultList = new ArrayList<>();
        ArrayList<String> variantsList = new ArrayList<>(Arrays.asList(variants.split(",")));
        variantsList.add(curAnswer);
        Collections.shuffle(variantsList);

        for (String curVariant:variantsList)
            resultList.add(curVariant.trim());

        return resultList;
    }

    /**
     * Функция добавляет к вариантам ответа номера.
     * @param variantsList - список вариантов.
     * @return Измененный список ответов.
     */
    private static ArrayList<String> addNumbers(ArrayList<String> variantsList) {
        ArrayList<String> resultVariantsList = new ArrayList<>();

        for (int i = 0; i < variantsList.size(); i++)
            resultVariantsList.add((i + 1) + ") " + variantsList.get(i));

        return resultVariantsList;
    }

    /**
     * Функция создает список правильных ответов на все вопросы текущей веб-страницы.
     * @param categoriesBlock - блок кода, в котором содержатся все вопросы этой веб-страницы.
     * @return Список правильных ответов.
     */
    private static ArrayList<String> parseAnswers(String categoriesBlock) {
        ArrayList<String> outputList = new ArrayList<>();

        Pattern answerPattern = Pattern.compile("<td>.+?<\\/td>");
        Matcher answerMatcher = answerPattern.matcher(categoriesBlock);

        int counter = 0;

        while(answerMatcher.find()) {
            counter++;
            String curLineAns = answerMatcher.group();
            String ans = curLineAns.substring(curLineAns.indexOf(">") + 1, curLineAns.lastIndexOf("<"));

            if (counter % 3 != 0)
                continue;

            outputList.add(ans);
        }

        return outputList;
    }
}
