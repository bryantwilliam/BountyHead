package com.gmail.gogobebe2.bountyhead.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class onBountyHeadSignCreateListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignChange(SignChangeEvent event) {
        if (event.isCancelled()) return;
        String[] lines = event.getLines();
        if (lines[0].equalsIgnoreCase("[sell]") && lines[1].equalsIgnoreCase("head")) {
            Player player = event.getPlayer();
            if (player.hasPermission("bountyhead.makesign")) {
                event.setLine(0, ChatColor.DARK_BLUE + " [Sell] ");
                event.setLine(1, " Head ");
                player.sendMessage(ChatColor.GREEN + "Head selling sign created!");
            }
            else {
                player.sendMessage(ChatColor.RED + "Error! You do not have permission to create head signs!");
            }
        }
    }
}