package com.gmail.gogobebe2.bountyhead;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class onSignRightClickListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onSign(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (BountyHead.isHeadSign(event.getClickedBlock())) {
                // TODO: check permission.
                ItemStack[] inventory = player.getInventory().getContents();
                int skullAmount = getSkullAmount(inventory);
                if (skullAmount == 0) {
                    player.sendMessage(ChatColor.RED + "Oops! You don't have any heads in your inventory!");
                } else {
                    sellSkull(player);
                }

            }
        }
    }

    private void sellSkull(Player player) {
        boolean isSneaking = player.isSneaking();
        // TODO: sell logic here.
    }

    private int getSkullAmount(ItemStack[] inventory) {
        int amount = 0;
        for (ItemStack item : inventory) {
            if (item.getType().equals(Material.SKULL_ITEM)) {
                amount += item.getAmount();
            }
        }
        return amount;
    }
}

