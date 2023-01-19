package work.blackbe.mirai.config;

import work.blackbe.mirai.BlackBE;
import work.blackbe.mirai.response.Info;
import work.blackbe.mirai.response.repository.Repository;
import work.blackbe.mirai.response.repository.Response;
import work.blackbe.mirai.utils.Network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Placeholder {
    public static Map<String, String> transfer(Info info, String target) {
        Map<String, String> map = new HashMap<>();
        map.put("qq", String.valueOf(info.qq));
        map.put("player_name", info.name);
        map.put("uuid", info.uuid);
        map.put("xuid", info.xuid);
        map.put("info", info.info);
        map.put("level", String.valueOf(info.level));
        map.put("detail_url", String.format("https://blackbe.work/detail/%s", info.uuid));

        String libSource = "";
        if (info.black_id.equals("1")) {
            libSource = "公开库";
        } else {
            HashMap<String, Object> header = new HashMap<>();
            String token = Config.INSTANCE.getToken();
            if (!token.equals("")) {
                header.put("Authorization", "Bearer "+token);
            }

            try {
                List<Repository> repositories;
                if (Global.REPOSITORIES_LIST.containsKey(token)) {
                    repositories = Global.REPOSITORIES_LIST.get(token);
                } else {
                    String result = Network.sendGetRequest(Global.API_PRIVATE_REPOSITORIES_LIST, header);
                    Response response = Global.GSON.fromJson(result, Response.class);
                    if (Global.REPOSITORIES_LIST.containsKey(token)) {
                        Global.REPOSITORIES_LIST.put(token, response.data.repositoriesList);
                    }
                    repositories = response.data.repositoriesList;
                }

                for (Repository repository : repositories) {
                    if (repository.uuid.equals(info.black_id)) {
                        libSource = repository.name;
                    }
                }
            } catch (Exception e) {
                BlackBE.INSTANCE.getLogger().error(e);
            }
        }
        map.put("black_id", libSource);

        map.put("target", target);

        return map;
    }
}
