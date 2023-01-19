package work.blackbe.mirai;

import work.blackbe.mirai.command.ReloadConfig;
import work.blackbe.mirai.config.Config;
import work.blackbe.mirai.event.Event;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;

public final class BlackBE extends JavaPlugin {
    public static final BlackBE INSTANCE = new BlackBE();

    private BlackBE() {
        super(new JvmPluginDescriptionBuilder("work.blackbe.mirai", "3.1.0")
                .name("BlackBE")
                .author("McPlus")
                .build());
    }

    @Override
    public void onEnable() {
        getLogger().info("\n"+
                "██████╗ ██╗      █████╗  ██████╗██╗  ██╗██████╗ ███████╗\n" +
                "██╔══██╗██║     ██╔══██╗██╔════╝██║ ██╔╝██╔══██╗██╔════╝\n" +
                "██████╔╝██║     ███████║██║     █████╔╝ ██████╔╝█████╗  \n" +
                "██╔══██╗██║     ██╔══██║██║     ██╔═██╗ ██╔══██╗██╔══╝  \n" +
                "██████╔╝███████╗██║  ██║╚██████╗██║  ██╗██████╔╝███████╗\n" +
                "╚═════╝ ╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═════╝ ╚══════╝");

        // 重载配置文件
        reloadPluginConfig(Config.INSTANCE);

        // 订阅事件
        Event.subscribeEvent();

        CommandManager.INSTANCE.registerCommand(ReloadConfig.INSTANCE, true);
    }
}