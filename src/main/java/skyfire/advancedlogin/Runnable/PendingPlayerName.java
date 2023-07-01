package skyfire.advancedlogin.Runnable;

import com.alibaba.fastjson2.JSONObject;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.UUID;

import static skyfire.advancedlogin.AdvancedLogin.*;

public class PendingPlayerName implements Runnable {

    private final String playerName;
    private final ProxiedPlayer player;

    public PendingPlayerName(ProxiedPlayer player){
        this.playerName = player.getName();
        this.player = player;
    }

    @Override
    public void run() {
        ArrayList<String> whitelists = (ArrayList<String>) whitelist.getStringList("whitelist");
        if(whitelist.getBoolean("enabled")){
            for(String name : whitelists){
                if(name.equals(playerName)) return;
            }
        }
        JSONObject result = api.sendGetWithSub(playerName);
        if(result == null){
            logger.info("玩家" + playerName + "无法查询到对应正版账号！断开连接...");
            player.disconnect(new TextComponent(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.checkFailed"))));
        }
        else{
            logger.info("玩家" + playerName + "查询到对应正版账号！");
            String uuid_official = result.getString("id");
            String uuid_player = player.getUniqueId().toString();
            while(uuid_player.contains("-")){
                uuid_player = uuid_player.replace("-", "");
            }
            logger.info("玩家UUID为" + uuid_player);
            if(uuid_player.equals(uuid_official)){
                logger.info("UUID匹配，放行");
            }
            else{
                logger.info("UUID不匹配，断开连接...");
                player.disconnect(new TextComponent(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.checkFailed"))));
            }
        }
    }
}
