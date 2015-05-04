package com.gmail.gogobebe2.bountyhead;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class onSignChangeListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignChange(SignChangeEvent event) {
        if (event.isCancelled()) return;
        String[] lines = event.getLines();
        // TODO: check for permission.
        if (lines[0].equalsIgnoreCase("[sell]") && lines[1].equalsIgnoreCase("head")) {
            event.setLine(0, ChatColor.DARK_BLUE + " [Sell] ");
            event.setLine(1, " Head ");
            Player player = event.getPlayer();
            player.sendMessage(ChatColor.GREEN + "Head selling sign created!");
        }
    }
}