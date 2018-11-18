package interfaces;

import chatbot.Request;
import java.util.ArrayList;

public interface InputOutput{
    Request getRequest();
    void print(String userId, ArrayList<String> outputStrings);
}
