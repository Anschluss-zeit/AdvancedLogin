package skyfire.advancedlogin.Handlers.Events;

import com.alibaba.fastjson2.JSONObject;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import skyfire.advancedlogin.Runnable.PendingPlayerName;

import java.util.ArrayList;

import static skyfire.advancedlogin.AdvancedLogin.*;

public class PostLoginEventHandler implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPostLogin(PostLoginEvent event){
        ProxiedPlayer player = event.getPlayer();
        if(config.getBoolean("AsyncRequest")){
            instance.getProxy().getScheduler().runAsync(instance, new PendingPlayerName(player));
        }
        else{
            String playerName = player.getName();
            String uuid_official, uuid_player;
            uuid_player = player.getUniqueId().toString();
            ArrayList<String> whitelists = (ArrayList<String>) whitelist.getStringList("whitelist");
            ArrayList<String> whitelists_uuid = (ArrayList<String>) whitelist.getStringList("uuid");
            ArrayList<String> blacklists = (ArrayList<String>) blacklist.getStringList("blacklist");
            if(whitelist.getBoolean("enabled")){
                for(String name : whitelists){
                    if(name.equals(playerName)){
                        logger.info("玩家" + playerName + "存在于豁免名单中，放行");
                        return;
                    }
                }
                for(String name : whitelists_uuid){
                    if(name.equals(uuid_player)){
                        logger.info("玩家" + playerName + "存在于豁免名单中，放行");
                        return;
                    }
                }
            }
            if(blacklist.getBoolean("enabled")){
                for(String name : blacklists){
                    if(name.equals(playerName)){
                        logger.info("玩家" + playerName + "存在于禁止名单中！断开连接...");
                        player.disconnect(new TextComponent(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.checkFailed"))));
                        return;
                    }
                }
            }
            JSONObject result = api.sendGetWithSub(playerName);
            while(uuid_player.contains("-")){
                uuid_player = uuid_player.replace("-", "");
            }
            boolean flag = false;
            if(result == null){
                logger.info("玩家" + playerName + "无法查询到对应正版账号！断开连接...");
                flag = true;
                player.disconnect(new TextComponent(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.checkFailed"))));
            }
            else{
                logger.info("玩家" + playerName + "查询到对应正版账号！");
                uuid_official = result.getString("id");
                logger.info("玩家UUID为" + uuid_player);
                if(uuid_player.equals(uuid_official)){
                    for(String name : whitelists_uuid){
                        if(name.equals(uuid_player)){
                            return;
                        }
                    }
                    whitelists_uuid.add(uuid_player);
                    whitelist.set("uuid", whitelists_uuid);
                    whitelist.save(); whitelist.reload();
                    logger.info("UUID匹配，放行");
                }
                else{
                    logger.info("UUID不匹配，断开连接...");
                    flag = true;
                    player.disconnect(new TextComponent(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.checkFailed"))));
                }
            }
            if(flag){
                for(String name : blacklists){
                    if(name.equals(uuid_player)){
                        return;
                    }
                }
                blacklists.add(uuid_player);
                blacklist.set("blacklist", blacklists);
                blacklist.save(); blacklist.reload();
            }
        }
    }
}
