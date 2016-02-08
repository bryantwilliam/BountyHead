package com.gmail.gogobebe2.bountyhead;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class onMobDropHeadListener implements Listener {
    @EventHandler
    private void onEntityDeathEvent(EntityDeathEvent event) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        Entity entity = event.getEntity();
        String owner = null;
        switch (entity.getType()) {
            case SKELETON:
                skull.setDurability((short) 0);
                break;
            case WITHER:
                skull.setDurability((short) 1);
                break;
            case ZOMBIE:
                skull.setDurability((short) 2);
                break;
            case CREEPER:
                skull.setDurability((short) 4);
                break;
            case PLAYER:
                owner = entity.getName();
                break;
            case BLAZE:
                owner = "Blaze";
                break;
            case CAVE_SPIDER:
                owner = "CaveSpider";
                break;
            case CHICKEN:
                owner = "Chicken";
                break;
            case COW:
                owner = "Cow";
                break;
            case ENDERMAN:
                owner = "Enderman";
                break;
            case GHAST:
                owner = "Ghast";
                break;
            case IRON_GOLEM:
                owner = "Golem";
                break;
            case MAGMA_CUBE:
                owner = "LavaSlime";
                break;
            case MUSHROOM_COW:
                owner = "MushroomCow";
                break;
            case OCELOT:
                owner = "Ocelot";
                break;
            case PIG:
                owner = "Pig";
                break;
            case PIG_ZOMBIE:
                owner = "PigZombie";
                break;
            case SHEEP:
                owner = "Sheep";
                break;
            case SLIME:
                owner = "Slime";
                break;
            case SPIDER:
                owner = "Spider";
                break;
            case SQUID:
                owner = "Squid";
                break;
            case VILLAGER:
                owner = "Villager";
                break;
        }

        if (owner != null) {
            skull.setDurability((short) 3);
            if (!(entity instanceof Player)) owner = "MHF_" + owner;
            skullMeta.setOwner(owner);
        }

        skull.setItemMeta(skullMeta);

        event.getDrops().add(skull);
    }
}
