package skyfire.advancedlogin.Handlers.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;

import static skyfire.advancedlogin.AdvancedLogin.blacklist;
import static skyfire.advancedlogin.AdvancedLogin.whitelist;

public class aloginHandler extends Command {

    public aloginHandler(){
        super("alogin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 1){
            if(args[0].equals("reload")){
                whitelist.reload();
                sender.sendMessage(new TextComponent("重载成功！"));
            }
            else{
                sender.sendMessage(new TextComponent("未知指令！"));
            }
        }
        else if(args.length == 2){
            if(args[0].equals("whitelist")){
                if(args[1].equals("enable")){
                    whitelist.set("enabled", true);
                    whitelist.save(); whitelist.reload();
                    sender.sendMessage(new TextComponent("设置成功！"));
                }
                else if(args[1].equals("disable")){
                    whitelist.set("enabled", false);
                    whitelist.save(); whitelist.reload();
                    sender.sendMessage(new TextComponent("设置成功！"));
                }
                else{
                    sender.sendMessage(new TextComponent("未知指令！"));
                }
            }
            else{
                sender.sendMessage(new TextComponent("未知指令！"));
            }
            if(args[0].equals("blacklist")){
                if(args[1].equals("enable")){
                    blacklist.set("enabled", true);
                    blacklist.save(); blacklist.reload();
                    sender.sendMessage(new TextComponent("设置成功！"));
                }
                else if(args[1].equals("disable")){
                    blacklist.set("enabled", false);
                    blacklist.save(); blacklist.reload();
                    sender.sendMessage(new TextComponent("设置成功！"));
                }
                else{
                    sender.sendMessage(new TextComponent("未知指令！"));
                }
            }
            else{
                sender.sendMessage(new TextComponent("未知指令！"));
            }
        }
        else if(args.length == 3){
            if(args[0].equals("whitelist")){
                ArrayList<String> whitelists = (ArrayList<String>) whitelist.getStringList("whitelist");
                if(args[1].equals("add")){
                    for(String name : whitelists){
                        if(name.equals(args[2])){
                            sender.sendMessage(new TextComponent("已有相同用户名！"));
                            return;
                        }
                    }
                    whitelists.add(args[2]);
                    whitelist.set("whitelist", whitelists);
                    whitelist.save(); whitelist.reload();
                    sender.sendMessage(new TextComponent("添加成功！"));
                }
                else if(args[1].equals("remove")){
                    for(int i = 0; i < whitelists.size(); i++){
                        if(whitelists.get(i).equals(args[2])){
                            whitelists.remove(i);
                            whitelist.set("whitelist", whitelists);
                            whitelist.save(); whitelist.reload();
                            sender.sendMessage(new TextComponent("删除成功！"));
                            return;
                        }
                    }
                    sender.sendMessage(new TextComponent("未找到用户名！"));
                }
                else {
                    sender.sendMessage(new TextComponent("未知指令！"));
                }
            }
            if(args[0].equals("blacklist")){
                ArrayList<String> blacklists = (ArrayList<String>) blacklist.getStringList("whitelist");
                if(args[1].equals("add")){
                    for(String name : blacklists){
                        if(name.equals(args[2])){
                            sender.sendMessage(new TextComponent("已有相同uuid！"));
                            return;
                        }
                    }
                    blacklists.add(args[2]);
                    blacklist.set("blacklist", blacklists);
                    blacklist.save(); blacklist.reload();
                    sender.sendMessage(new TextComponent("添加成功！"));
                }
                else if(args[1].equals("remove")){
                    for(int i = 0; i < blacklists.size(); i++){
                        if(blacklists.get(i).equals(args[2])){
                            blacklists.remove(i);
                            blacklist.set("blacklist", blacklists);
                            blacklist.save(); blacklist.reload();
                            sender.sendMessage(new TextComponent("删除成功！"));
                            return;
                        }
                    }
                    sender.sendMessage(new TextComponent("未找到uuid！"));
                }
                else {
                    sender.sendMessage(new TextComponent("未知指令！"));
                }
            }
        }
        else {
            sender.sendMessage(new TextComponent("未知指令！"));
        }
    }
}
