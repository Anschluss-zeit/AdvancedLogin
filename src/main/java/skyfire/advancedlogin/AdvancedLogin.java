package skyfire.advancedlogin;

import net.md_5.bungee.api.plugin.Plugin;
import skyfire.advancedlogin.Handlers.Commands.aloginHandler;
import skyfire.advancedlogin.Handlers.Events.PostLoginEventHandler;

import java.util.logging.Logger;

public final class AdvancedLogin extends Plugin {
    public static Logger logger;
    public static Plugin instance;
    public static ConfigManager config, whitelist;
    public static NetworkManager api;

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = getLogger();
        instance = this;
        logger.info("正在加载 AdvancedLogin 插件...");
        config = new ConfigManager("config.yml");
        whitelist = new ConfigManager("whitelist.yml");
        api = new NetworkManager("https://api.mojang.com/users/profiles/minecraft/");
        getProxy().getPluginManager().registerListener(this, new PostLoginEventHandler());
        getProxy().getPluginManager().registerCommand(this, new aloginHandler());
        logger.info("加载完成！");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("正在禁用 AdvancedLogin 插件...");
        logger.info("禁用完成！");
    }
}
