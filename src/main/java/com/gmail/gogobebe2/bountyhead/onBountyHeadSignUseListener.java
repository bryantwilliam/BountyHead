package com.gmail.gogobebe2.bountyhead;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class onBountyHeadSignUseListener implements Listener {
    BountyHead plugin;

    public onBountyHeadSignUseListener(BountyHead plugin) {
        this.plugin = plugin;
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void onHeadPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        if (event.getBlockPlaced().getType().equals(Material.SKULL) && Utils.isHeadSign(event.getBlockAgainst())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignRightClick(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (Utils.isHeadSign(event.getClickedBlock())) {
                plugin.sellSkull(player);
            }
        }
    }
}

