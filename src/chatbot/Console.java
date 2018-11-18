package chatbot;

import enums.RequestFrom;
import interfaces.InputOutput;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Класс консоли(User Interface), который реализовывает интерфейсы INPUT, Output для правильного общения с юзером.
 */
public class Console implements InputOutput {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final String CONSOLE_ID = "ConsoleUser";

    @Override
    public Request getRequest() {
        return new Request(CONSOLE_ID, INPUT.nextLine(), RequestFrom.Console);
    }

    @Override
    public void print(String userId, ArrayList<String> outputStrings) {
        for (String line: outputStrings){
            System.out.println(line);
        }
    }
}
