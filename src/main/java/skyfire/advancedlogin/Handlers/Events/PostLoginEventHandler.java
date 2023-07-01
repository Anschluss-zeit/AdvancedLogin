package skyfire.advancedlogin.Handlers.Events;

import com.alibaba.fastjson2.JSONObject;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import skyfire.advancedlogin.Runnable.PendingPlayerName;

import static skyfire.advancedlogin.AdvancedLogin.*;

public class PostLoginEventHandler implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPostLogin(PostLoginEvent event){
        ProxiedPlayer player = event.getPlayer();
        if(config.getBoolean("AsyncRequest")){
            instance.getProxy().getScheduler().runAsync(instance, new PendingPlayerName(player));
        }
        else{
            JSONObject result = api.sendGetWithSub(player.getName());
            String errorMessage = result.getString("errorMessage");
            if(errorMessage != null && !errorMessage.equals("")){
                player.disconnect(new TextComponent(config.getString("Messages.checkFailed")));
            }
        }
    }
}
