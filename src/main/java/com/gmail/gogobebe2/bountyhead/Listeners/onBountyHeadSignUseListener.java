package com.gmail.gogobebe2.bountyhead.Listeners;

import com.gmail.gogobebe2.bountyhead.BountyHead;
import net.ess3.api.MaxMoneyException;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.math.BigDecimal;

public class onBountyHeadSignUseListener implements Listener {
    BountyHead plugin;

    public onBountyHeadSignUseListener(BountyHead plugin) {
        this.plugin = plugin;
    }


    @EventHandler (priority = EventPriority.NORMAL)
    public void onHeadPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        if (event.getBlockPlaced().getType().equals(Material.SKULL) && BountyHead.isHeadSign(event.getBlockAgainst())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignRightClick(PlayerInteractEvent event) {
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

    private HeadType getHeadType(String owner) {
        if (owner.contains("MHF_")) {
            owner = owner.replaceFirst("MHF_", "");
        }
        for (String headName : plugin.getConfig().getConfigurationSection("prices.mobs").getKeys(false)) {
            if (headName.equalsIgnoreCase(owner)) {
                return HeadType.MOB;
            }
        }
        for (String headName : plugin.getConfig().getConfigurationSection("prices.blocks").getKeys(false)) {
            if (headName.equalsIgnoreCase(owner)) {
                return HeadType.BLOCK;
            }
        }
        for (String headName : plugin.getConfig().getConfigurationSection("prices.bonus").getKeys(false)) {
            if (headName.equalsIgnoreCase(owner)) {
                return HeadType.BONUS;
            }
        }

        return HeadType.PLAYER;
    }

    private double getSkullPrice(ItemStack skull) {
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        String head;
        if (skullMeta.hasOwner()) {
            head = skullMeta.getOwner();
        } else if (skullMeta.hasDisplayName()) {
            head = skullMeta.getDisplayName();
        } else if (skull.getDurability() == 0) {
            head = "Skeleton";
        } else if (skull.getDurability() == 1) {
            head = "WSkeleton";
        } else if (skull.getDurability() == 2) {
            head = "Zombie";
        } else if (skull.getDurability() == 3) {
            head = "Head";
        } else if (skull.getDurability() == 4) {
            head = "Creeper";
        } else {
            head = "Unknown";
        }
        HeadType headType = getHeadType(head);
        if (plugin.getConfig().isSet("prices.all")) {
            return plugin.getConfig().getDouble("prices.all");
        } else if (plugin.getConfig().isSet("prices.allMobs") && headType.equals(HeadType.MOB)) {
            return plugin.getConfig().getDouble("prices.allMobs");
        } else if (plugin.getConfig().isSet("prices.allBlocks") && headType.equals(HeadType.BLOCK)) {
            return plugin.getConfig().getDouble("prices.allBlocks");
        } else if (plugin.getConfig().isSet("prices.allBonus") && headType.equals(HeadType.BONUS)) {
            return plugin.getConfig().getDouble("prices.allBonus");
        } else if (headType.equals(HeadType.PLAYER)) {
            if (plugin.getConfig().isSet("prices.players.specificPlayer." + head)) {
                return plugin.getConfig().getDouble("prices.players.specificPlayer." + head);
            } else {
                double balance;
                try {
                    balance = BountyHead.getEss3().getUser(head).getMoney().doubleValue();
                } catch (NullPointerException exc) {
                    balance = 1;
                }
                return (plugin.getConfig().getDouble("prices.players.percentage") / 100) * balance;
            }
        } else {
            if (head.contains("MHF_")) {
                head = head.replaceFirst("MHF_", "");
            }
            if (headType.equals(HeadType.MOB)) {
                return plugin.getConfig().getDouble("prices.mobs." + head);
            } else if (headType.equals(HeadType.BLOCK)) {
                return plugin.getConfig().getDouble("prices.blocks." + head);
            } else if (headType.equals(HeadType.BONUS)) {
                return plugin.getConfig().getDouble("prices.bonus." + head);
            } else {
                throw new NumberFormatException();
            }
        }
    }


    private void sellSkull(Player player) {
        PlayerInventory inventory = player.getInventory();
        final boolean IS_SNEAKING = player.isSneaking();
        final int AMOUNT;
        int slot;
        if (inventory.getItemInHand() != null && inventory.getItemInHand().getType().equals(Material.SKULL_ITEM)) {
            slot = inventory.getHeldItemSlot();
        } else {
            slot = inventory.first(Material.SKULL_ITEM);
        }
        if (slot == -1) {
            throw new NullPointerException("Null pointer exception! There are no items in the player's inventory that have the Material of Material.SKULL_ITEM");
        }
        ItemStack item = inventory.getItem(slot);
        double price = getSkullPrice(item);
        if (IS_SNEAKING) {
            AMOUNT = item.getAmount();
        } else {
            AMOUNT = 1;
        }
        price *= AMOUNT;

        try {
            BountyHead.getEss3().getUser(player).giveMoney(BigDecimal.valueOf(price));
        } catch (MaxMoneyException e) {
            player.sendMessage(ChatColor.DARK_RED + "Error! Max money limit reached! Please report this error to the server administrator!");
        }
        item.setAmount(item.getAmount() - AMOUNT);
        inventory.setItem(slot, item);
        player.updateInventory();
        player.sendMessage(ChatColor.BLUE + "Sold " + ChatColor.DARK_GREEN + ChatColor.BOLD + AMOUNT + ChatColor.BLUE
                + ((AMOUNT == 1) ? " head " : " heads") +  " for " + ChatColor.DARK_GREEN + ChatColor.BOLD + plugin.getConfig().getString("currencySymbol") + price + ChatColor.BLUE + ".");
    }
}

