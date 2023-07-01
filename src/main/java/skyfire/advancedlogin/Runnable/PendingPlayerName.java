package skyfire.advancedlogin.Runnable;

import com.alibaba.fastjson2.JSONObject;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;

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
            player.disconnect(new TextComponent(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.checkFailed"))));
        }
    }
}
