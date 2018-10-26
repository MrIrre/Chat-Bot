package chatbot;

import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;

import interfaces.Input;
import interfaces.Output;

import java.util.ArrayList;


public class VkVersion implements Input, Output {
    private static final Group GROUP = new Group(171821026,
            "8c20fd3bb43b51bf31d8704d967360ab105082caccb01b1b7ad3c14d4f5f3131d6bedb9aac123765961eb");
    private Request curRequest;

    @Override
    public Request getRequest() {
        Request request = curRequest;
        curRequest = null;

        GROUP.onEveryMessage(message ->
                curRequest = new Request(message.authorId().toString(), message.getText()));

        return request;
    }

    @Override
    public void print(String userId, ArrayList<String> outputStrings) {
        new Message()
                .from(GROUP)
                .to(Integer.parseInt(userId))
                .text(String.join("\n", outputStrings))
                .send();
    }
}
