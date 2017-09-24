package plugin.peculiarityy.main;



import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

import static plugin.peculiarityy.main.Main.plugin;

public class PlayerEat implements Listener {

    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }


    private ArrayList<String> effects;
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e){

        String path = "hand.foods.";

        ItemStack food = e.getItem();

        Player player = e.getPlayer();

        effects = (ArrayList<String>) plugin.getConfig().getStringList(path + food.getType().toString() + ".effects");

        if(plugin.getConfig().contains(path + food.getType().toString())) {
            player.sendMessage(format(plugin.getConfig().getString("prefix") + plugin.getConfig().getString(path + food.getType().toString() + ".transformation-message")));
            DisguiseAPI.disguiseToAll(player, new MobDisguise(DisguiseType.valueOf(plugin.getConfig().getString(path + food.getType().toString() + ".transformation").toUpperCase())));
            for (String effect : effects) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect), (plugin.getConfig().getInt(path + food.getType().toString() + ".duration") * 20), 1));
            }

            new BukkitRunnable() {
                public void run() {
                    DisguiseAPI.undisguiseToAll(player);
                    player.sendMessage(format(plugin.getConfig().getString("prefix") + plugin.getConfig().getString("undisguise-message")));
                }

            }.runTaskLater(plugin, plugin.getConfig().getInt(path + food.getType().toString() + ".duration") * 20);
            return;
        }
    }
}

