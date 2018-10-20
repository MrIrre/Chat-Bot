package iomanager;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class QuestionsFromSite {
    public static final int NumberOfPages = 299;

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
        String categories = quizText.substring(quizText.indexOf("q-list__table") + "q-list__table".length() + 2);
        String cats = categories.substring(0, categories.indexOf("</table>"));

        Pattern quotPattern = Pattern.compile("&quot;");
        Matcher quotMatcher = quotPattern.matcher(cats);
        cats = quotMatcher.replaceAll("");

        Pattern questionPattern = Pattern.compile("<a href.+?<\\/a>");
        Matcher m = questionPattern.matcher(cats);

        Pattern varianstsPattern = Pattern.compile("quiz-answers.+?<");
        Matcher variantsMatcher = varianstsPattern.matcher(cats);

        ArrayList<String> answers = parseAnswers(cats);

        int counter = 0;
        while (m.find() && variantsMatcher.find() && counter < answers.size()) {
            String curLine = m.group();
            String curVariants = variantsMatcher.group();

            String quest = curLine.substring(curLine.indexOf(">") + 1, curLine.lastIndexOf("<"));
            String variants = curVariants.substring(curVariants.indexOf(":") + 2, curVariants.lastIndexOf("<"));
            variants = variants.replaceAll("\\s+", " ");
            variants = variants.replaceAll(", ", ",");

            if (variants.charAt(variants.length() - 1) == ' ') {
                variants = variants.substring(0, variants.length() - 1);
            }

            String curAnswer = answers.get(counter);
            ArrayList<String> variantsList = createVariantsList(variants, curAnswer);

            Set<String> answerSet = new HashSet<>();
            answerSet.add(curAnswer.toLowerCase());
            questionAndAnswer.put(quest + variantsList, answerSet);
            counter++;
        }

        return questionAndAnswer;
    }

    private static ArrayList<String> createVariantsList(String variants, String curAnswer) {
        ArrayList<String> variantsList = new ArrayList<>(Arrays.asList(variants.split(",")));
        variantsList.add(curAnswer);

        for (int k = 0; k < variantsList.size(); k++) {
            var curVariant = variantsList.get(k);
            if (curVariant.charAt(0) == ' '){
                variantsList.remove(k);
                variantsList.add(curVariant.substring(1));
            }
        }

        Collections.shuffle(variantsList);
        return variantsList;
    }

    private static ArrayList<String> parseAnswers(String cats) {
        ArrayList<String> outputList = new ArrayList<>();

        Pattern answerPattern = Pattern.compile("<td>.+?<\\/td>");
        Matcher answerMatcher = answerPattern.matcher(cats);

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
