package chatbot;

import enums.Version;
import interfaces.Input;
import interfaces.Output;

import java.util.ArrayList;
import java.util.Scanner;

public class Console implements Input, Output {
    private static final Scanner input = new Scanner(System.in);

    @Override
    public Request GetRequest() {
        return new Request("ConsoleUser", input.nextLine(), Version.Console);
    }

    @Override
    public void Print(ArrayList<String> outputStrings) {
        for (String line: outputStrings){
            System.out.println(line);
        }
    }
}
