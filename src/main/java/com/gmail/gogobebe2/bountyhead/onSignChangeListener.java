package com.gmail.gogobebe2.bountyhead;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class onSignChangeListener implements Listener{

    @EventHandler(priority = EventPriority.HIGH)
    public void onSignChange(SignChangeEvent event) {
        if (event.isCancelled()) return;
        Block block = event.getBlock();
        if (BountyHead.isSign(block)) {
            Sign sign = (Sign) block;
            // TODO: check for permission.
            if (createHeadSign(sign)) {
                Player player = event.getPlayer();
                player.sendMessage(ChatColor.GREEN + "Head selling sign created!");
            }
        }
    }

    private static boolean createHeadSign(Sign sign) {
        if (sign.getLine(0).equalsIgnoreCase("[sell]") && sign.getLine(1).equalsIgnoreCase("head")) {
            sign.setLine(0, ChatColor.DARK_BLUE + " [Sell] ");
            sign.setLine(1, " Head ");
            sign.update();
            return true;
        }
        return false;
    }
}