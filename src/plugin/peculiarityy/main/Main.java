package plugin.peculiarityy.main;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin{
    public static Main plugin;

    public void onEnable(){
        saveDefaultConfig();
        plugin = this;
        Bukkit.getPluginManager().registerEvents(new PlayerEat(), this);
    }
}
