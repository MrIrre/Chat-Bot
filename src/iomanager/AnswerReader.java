package iomanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Парсер вариаций ответов для пользователя.
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
