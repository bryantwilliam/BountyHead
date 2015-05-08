package com.gmail.gogobebe2.bountyhead.Listeners;

import com.gmail.gogobebe2.bountyhead.BountyHead;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class onSignRightClickListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSign(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (BountyHead.isHeadSign(event.getClickedBlock())) {
                // TODO: check permission.
                if (!player.getInventory().contains(Material.SKULL_ITEM, 1)) {
                    player.sendMessage(ChatColor.RED + "Oops! You don't have any heads in your inventory!");
                } else {
                    sellSkull(player);
                }

            }
        }
    }

    private void sellSkull(Player player) {
        // TODO: balance multiplier.
        Inventory inventory = player.getInventory();
        final boolean IS_SNEAKING = player.isSneaking();
        final int AMOUNT;
        if (IS_SNEAKING) {
            if (inventory.contains(Material.SKULL_ITEM, 64))
                AMOUNT = 64;
            else {
                AMOUNT = inventory.getItem(inventory.first(Material.SKULL_ITEM)).getAmount();
            }
        } else {
            AMOUNT = 1;
        }
        player.sendMessage("DEBUG MESSAGE: " + AMOUNT);
        int amountToSell = AMOUNT;
        while (true) {
            int slot = inventory.first(Material.SKULL_ITEM);
            ItemStack item = inventory.getItem(slot);
            int itemAmount = item.getAmount();
            if (IS_SNEAKING) {
                if (amountToSell > itemAmount) {
                    amountToSell -= itemAmount;
                } else {
                    amountToSell = 0;
                }
                item.setAmount(itemAmount - amountToSell);
                if (amountToSell == 0) {
                    inventory.setItem(slot, item);
                    break;
                }
            } else {
                item.setAmount(itemAmount - 1);
                inventory.setItem(slot, item);
                break;
            }
        }
        player.updateInventory();
        player.sendMessage(ChatColor.GREEN + "Sold " + AMOUNT + " heads.");
    }
}

