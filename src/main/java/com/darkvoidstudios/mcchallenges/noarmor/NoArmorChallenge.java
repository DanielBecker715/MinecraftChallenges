package com.darkvoidstudios.mcchallenges.noarmor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NoArmorChallenge {
    public static void replaceArmor(Player player, Material material) {
        player.getInventory().setHelmet(new ItemStack(material));
        player.getInventory().setChestplate(new ItemStack(material));
        player.getInventory().setLeggings(new ItemStack(material));
        player.getInventory().setBoots(new ItemStack(material));
    }
}
