package iomanager;

import java.io.*;
import java.util.ArrayList;

/**
 * Построчный парсер. На данный момент используется для парса слов виселицы и реплик чат-бота.
 */
public class AnswerReader {
    public static ArrayList<String> ParseAnswersFromFile(String fileName){
        ArrayList<String> curAnswers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                curAnswers.add(line);
            }
        }
        catch (IOException  e){
            e.printStackTrace();
        }

        return curAnswers;
    }
}
