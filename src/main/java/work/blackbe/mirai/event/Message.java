package work.blackbe.mirai.event;

import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private static final List<messageHandle> handles = new ArrayList<>();

    public interface messageHandle {
        void run(MessageEvent e);
    }

    public static void addHandle(messageHandle handle) {
        handles.add(handle);
    }

    public static void subscribe() {
        GlobalEventChannel.INSTANCE.subscribeAlways(MessageEvent.class, e -> handles.forEach(handle -> handle.run(e)));
    }

    public enum MessageType {
        CHECK, CHAT
    }
}
