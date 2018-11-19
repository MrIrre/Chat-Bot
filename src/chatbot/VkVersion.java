package chatbot;

import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;

import enums.RequestFrom;
import interfaces.InputOutput;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;


public class VkVersion implements InputOutput {
    private static final Group GROUP = new Group(171821026,
            "8c20fd3bb43b51bf31d8704d967360ab105082caccb01b1b7ad3c14d4f5f3131d6bedb9aac123765961eb");
    private Request curRequest = null;
    private ConcurrentLinkedQueue<Request> requestQueue = new ConcurrentLinkedQueue<>();

    public Request getRequest() {

        GROUP.onEveryMessage(message ->
                requestQueue.add(new Request(message.authorId().toString(), message.getText(), RequestFrom.VK)));

        return requestQueue.poll();
    }

    public void print(String userId, ArrayList<String> outputStrings) {
        new Message()
                .from(GROUP)
                .to(Integer.parseInt(userId))
                .text(String.join("\n", outputStrings))
                .send();
    }
}
