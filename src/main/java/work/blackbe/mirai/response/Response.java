package work.blackbe.mirai.response;

import work.blackbe.mirai.config.Config;
import work.blackbe.mirai.config.Placeholder;
import work.blackbe.mirai.event.GroupMessage;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.QuoteReply;
import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

import static work.blackbe.mirai.event.Event.qqTemp;

public class Response {
    public boolean success;
    public int status;
    public String message;
    public String version;
    public String codename;
    public int time;
    public Data data;

    public void handle(GroupMessageEvent event, GroupMessage.MessageType type, String target) {
        switch (status) {
            case 2000:
                for (Info info : data.info) {
                    qqTemp.put(info.qq, this);
                    Map<String, String> valMap = Placeholder.transfer(info, target);
                    StringSubstitutor sub = new StringSubstitutor(valMap);
                    String resolved = sub.replace(Config.INSTANCE.getResponse());

                    event.getSubject().sendMessage(new QuoteReply(event.getMessage()).plus(resolved));
                }
                break;

            case 2001:
            case 4000:
            case 5000:
                if (type.equals(GroupMessage.MessageType.CHECK)) {
                    event.getSubject().sendMessage(new QuoteReply(event.getMessage()).plus(message));
                    break;
                }
                break;
        }
    }
}
