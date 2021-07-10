package tr.donsuzturk.spawnerkiller;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    FileConfiguration config = getConfig();

    public void onEnable() {
        super.onEnable();
        config.addDefault("Settings.spawnerKiller", true);
        config.options().copyDefaults(true);
        saveConfig();
        getLogger().info("==============================");
        getLogger().info("Plugin enabled!");
        getLogger().info("==============================");
        this.getServer().getPluginManager().registerEvents(this, this);
        reloadConfig();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void mobSpawn(final CreatureSpawnEvent sp) {
        if (config.getBoolean("Settings.spawnerKiller")) {
            if (sp.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER) && !sp.getEntity().isDead()) {
                sp.getEntity().damage(2.0E9);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equals("spawnerkiller")) return super.onCommand(sender, command, label, args);
        reloadConfig();
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GRAY + "*" + ChatColor.GREEN + " Plugin reloaded!");
        sender.sendMessage("");
        return true;
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        saveDefaultConfig();
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
    }

}