package work.blackbe.mirai.event;

import work.blackbe.mirai.BlackBE;
import work.blackbe.mirai.config.Config;
import work.blackbe.mirai.config.Global;
import work.blackbe.mirai.response.Response;
import work.blackbe.mirai.utils.Network;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event {
    public static Map<Long, Response> qqTemp = new HashMap<>();

    public static void subscribeEvent() {
        Message.addHandle(event -> {
            String message = event.getMessage().contentToString();

            Pattern pattern = Pattern.compile(Config.INSTANCE.getExpression());
            Matcher matcher = pattern.matcher(message);
            boolean isMatch = matcher.find();

            if (isMatch) {
                String target = matcher.group(1).trim();
                target = URLEncoder.encode(target, StandardCharsets.UTF_8);

                String param = String.format("name=%s&qq=%s&xuid=%s", target, target, target);

                HashMap<String, Object> header = new HashMap<>();
                if (!Config.INSTANCE.getToken().equals("")) {
                    header.put("Authorization", "Bearer "+Config.INSTANCE.getToken());
                }

                String request = Global.ADDRESS.concat("check?").concat(param);

                try {
                    String result = Network.sendGetRequest(request, header);

                    Gson gson = new Gson();
                    Response response = gson.fromJson(result, Response.class);

                    response.handle(event, Message.MessageType.CHECK, target);
                } catch (Exception e) {
                    BlackBE.INSTANCE.getLogger().error(e);
                }
            }
        });

        Message.addHandle(event -> {
            Long qq = event.getSender().getId();
            if (!qqTemp.containsKey(qq)) {
                String param = String.format("qq=%s", qq);

                HashMap<String, Object> header = new HashMap<>();
                if (!Config.INSTANCE.getToken().equals("")) {
                    header.put("Authorization", "Bearer "+Config.INSTANCE.getToken());
                }

                String request = Global.ADDRESS.concat("check?").concat(param);

                try {
                    String result = Network.sendGetRequest(request, header);

                    Gson gson = new Gson();
                    Response response = gson.fromJson(result, Response.class);

                    response.handle(event, Message.MessageType.CHAT, event.getSenderName());
                } catch (Exception e) {
                    BlackBE.INSTANCE.getLogger().error(e);
                }
            } else {
                qqTemp.get(qq).handle(event, Message.MessageType.CHAT, event.getSenderName());
            }
        });

        Message.subscribe();


    }
}
