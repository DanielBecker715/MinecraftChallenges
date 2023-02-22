package com.darkvoidstudios.mcchallenges.randomitem;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;


public class RandomItem {
    static Challenge challenge = Challenge.getInstance();

    public static void distributeRandomItemsToAllPlayers() {
        if (challenge.isChallengeActive() && challenge.isRandomItemChallengeActive()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Random random = new Random();
                int maxAmount = (int) (Math.random() * (64 - 1)) + 1;
                int material = random.nextInt(Material.values().length);
                int amount = random.nextInt(maxAmount);
                player.getInventory().addItem(new ItemStack(Material.values()[material], Math.min(Material.values()[material].getMaxStackSize(), amount)));
            }
        }
    }
}
