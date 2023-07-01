package skyfire.advancedlogin;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import static skyfire.advancedlogin.AdvancedLogin.instance;

public class ConfigManager {

    private Configuration config;
    private final File file;

    public ConfigManager(String filename){
        File folder = instance.getDataFolder();
        if (!folder.exists()) folder.mkdir();
        file = new File(folder, filename);
        if (!file.exists()) {
            try (InputStream in = instance.getResourceAsStream(filename)) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try{
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public synchronized void reload(){
        try{
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public synchronized void save(){
        try{
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public synchronized String getString(String path){
        return config.getString(path);
    }

    public synchronized int getInt(String path){
        return config.getInt(path);
    }

    public synchronized long getLong(String path){
        return config.getLong(path);
    }

    public synchronized boolean getBoolean(String path){
        return config.getBoolean(path);
    }

    public synchronized List<Long> getLongList(String path){
        return config.getLongList(path);
    }

    public synchronized List<Integer> getIntList(String path){
        return config.getIntList(path);
    }

    public synchronized List<String> getStringList(String path){
        return config.getStringList(path);
    }

    public synchronized List<Boolean> getBooleanList(String path){
        return config.getBooleanList(path);
    }

    public synchronized void set(String path, Object value){
        config.set(path, value);
    }
}
