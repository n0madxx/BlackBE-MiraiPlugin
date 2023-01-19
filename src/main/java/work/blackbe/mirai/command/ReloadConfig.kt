package work.blackbe.mirai.command

import work.blackbe.mirai.BlackBE
import work.blackbe.mirai.config.Config
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.ConsoleCommandSender
import net.mamoe.mirai.console.plugin.jvm.reloadPluginConfig

object ReloadConfig : CompositeCommand(BlackBE.INSTANCE, "blackbe") {
    @SubCommand("reload")
    suspend fun reload(context: CommandContext) {
        if (context.sender is ConsoleCommandSender) {
            BlackBE.INSTANCE.reloadPluginConfig(Config)
            context.sender.sendMessage("重载配置文件成功")
        }
    }
}