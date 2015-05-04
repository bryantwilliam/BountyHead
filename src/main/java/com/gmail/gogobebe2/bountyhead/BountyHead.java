package com.gmail.gogobebe2.bountyhead;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class BountyHead extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Starting up com.gmail.gogobebe2.bountyhead.BountyHead. If you have any bugs or problems, email me at: gogobebe2@gmail.com");
        getServer().getPluginManager().registerEvents(new onSignChangeListener(), this);
        getServer().getPluginManager().registerEvents(new onSignRightClickListener(), this);
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
}
