package com.darkvoidstudios.mcchallenges.randomitem.schedulers;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class RandomItemTimer extends BukkitRunnable {

    private final Plugin plugin;
    private Challenge challenge;

    public RandomItemTimer(Plugin plugin, Challenge challenge) {
        this.plugin = plugin;
        this.challenge = challenge;
    }

    @Override
    public void run() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (challenge.isChallengeActive() && challenge.isRandomItemChallengeActive()) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Random random = new Random();
                    int maxAmount = (int) (Math.random() * (64 - 1)) + 1;
                    int material = random.nextInt(Material.values().length);
                    int amount = random.nextInt(maxAmount);
                    player.getInventory().addItem(new ItemStack(Material.values()[material], Math.min(Material.values()[material].getMaxStackSize(), amount)));
                }
            }
        }, 0, 600);
    }
}
