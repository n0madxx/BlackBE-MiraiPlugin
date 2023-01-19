package work.blackbe.mirai.event;

import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.GroupMessageEvent;

import java.util.ArrayList;
import java.util.List;

public class GroupMessage {
    private static final List<groupMessageHandle> handles = new ArrayList<>();

    public interface groupMessageHandle {
        void run(GroupMessageEvent e);
    }

    public static void addHandle(groupMessageHandle handle) {
        handles.add(handle);
    }

    public static void subscribe() {
        GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, e -> handles.forEach(handle -> handle.run(e)));
    }

    public enum MessageType {
        CHECK, CHAT
    }
}
