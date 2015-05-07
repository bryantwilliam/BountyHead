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
                int skullAmount = getSkullAmount(player.getInventory());
                if (skullAmount == 0) {
                    player.sendMessage(ChatColor.RED + "Oops! You don't have any heads in your inventory!");
                } else {
                    sellSkull(player);
                }

            }
        }
    }

    private void sellSkull(Player player) {
        // TODO: balance multiplier.
        boolean isSneaking = player.isSneaking();
        int amount = isSneaking ? 64 : 1;
        player.sendMessage(ChatColor.GREEN + "Sold " + amount + " heads.");
        while (true) {
            Inventory inventory = player.getInventory();
            int slot = inventory.first(Material.SKULL_ITEM);
            ItemStack item = inventory.getItem(slot);
            amount -= item.getAmount();
            inventory.remove(item);
            if (amount <= 0) {
                break;
            }
        }
    }

    private int getSkullAmount(Inventory inventory) {
        ItemStack[] contents = inventory.getContents();
        int amount = 0;
        if (contents.length != 0) {
            for (ItemStack item : contents) {
                if (item != null && item.getType().equals(Material.SKULL_ITEM)) {
                    amount += item.getAmount();
                }
            }
        }
        return amount;
    }
}

