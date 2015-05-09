package com.gmail.gogobebe2.bountyhead;

import com.gmail.gogobebe2.bountyhead.Listeners.onBountyHeadSignCreateListener;
import com.gmail.gogobebe2.bountyhead.Listeners.onBountyHeadSignUseListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class BountyHead extends JavaPlugin {
    public static Economy economy = null;

    @Override
    public void onEnable() {
        getLogger().info("Starting up BountyHead. If you have any bugs or problems, email me at: gogobebe2@gmail.com");
        if (!setupEconomy()) {
            getLogger().severe("Error!!! No economy plugin found!!!");
        }
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new onBountyHeadSignCreateListener(), this);
        getServer().getPluginManager().registerEvents(new onBountyHeadSignUseListener(this), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO: commands.
        return false;
    }

    public static boolean isHeadSign(Block block) {
        if (!(block.getState() instanceof Sign)) {
            return false;
        }
        Sign sign = (Sign) block.getState();
        String[] signLines = sign.getLines();
        return signLines[0].equalsIgnoreCase(ChatColor.DARK_BLUE + " [Sell] ") && signLines[1].equalsIgnoreCase(" Head ");
    }


    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }
}
