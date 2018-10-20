package iomanager;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionsReader
{
    public static final int NumberOfPages = 299;

	public static Map<String, Set<String>> GetDataFromFile(String fileName){
		BufferedReader reader;
		Map<String, Set<String>> questionsAndAnswers = new HashMap<>();

		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();

			while (line != null) {
				String[] curQuestionAndAnswer = line.split("::");
				Set<String> curAnswers = new HashSet<>(Arrays.asList(curQuestionAndAnswer[1].split(",")));
				questionsAndAnswers.put(curQuestionAndAnswer[0], curAnswers);
				line = reader.readLine();
			}

			reader.close();

		}
		catch (IOException e) {
			e.printStackTrace();
		}

        return questionsAndAnswers;
	}
}
