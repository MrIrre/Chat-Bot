package chatbot;

import enums.Version;
import interfaces.Input;
import interfaces.Output;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Класс консоли(User Interface), который реализовывает интерфейсы INPUT, Output для правильного общения с юзером.
 */
public class Console implements Input, Output {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final String CONSOLE_ID = "ConsoleUser";

    @Override
    public Request getRequest() {
        return new Request(CONSOLE_ID, INPUT.nextLine(), Version.Console);
    }

    @Override
    public void print(String userId, ArrayList<String> outputStrings) {
        for (String line: outputStrings){
            System.out.println(line);
        }
    }
}
