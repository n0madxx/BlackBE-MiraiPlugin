package work.blackbe.mirai.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value


object Config : AutoSavePluginConfig("Config") {
    val expression by value("^查云黑(.+)$")
    val response by value("QQ \${qq} 存在违规行为\n" +
            "================\n" +
            "玩家ID: \${player_name}\n" +
            "UUID: \${uuid}\n" +
            "XUID: \${xuid}\n" +
            "库来源: \${black_id}\n" +
            "记录原因: \${info}\n" +
            "危险等级: \${level}\n" +
            "玩家QQ: \${qq}\n" +
            "详细信息：\${detail_url}\n"+
            "================\n")
    val token by value("")
}