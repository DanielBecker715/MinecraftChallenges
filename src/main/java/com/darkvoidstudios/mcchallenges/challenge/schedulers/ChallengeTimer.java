package com.darkvoidstudios.mcchallenges.challenge.schedulers;

import com.darkvoidstudios.mcchallenges.challenge.models.ActionbarTimer;
import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.randomitem.RandomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Instant;

public class ChallengeTimer extends BukkitRunnable {

    private static final ChallengeTimer challengeTimer = new ChallengeTimer();

    final Plugin plugin = getPlugin();
    Challenge challenge = Challenge.getInstance();
    ActionbarTimer actionbarTimer = ActionbarTimer.getInstance();

    public static ChallengeTimer getInstance() {
        return challengeTimer;
    }



    @Override
    public void run() {
        actionbarTimer.setupTimer();
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (challenge.isRandomItemChallengeActive() && actionbarTimer.getCurrentSeconds() == 30) {
                RandomItem.distributeRandomItemsToAllPlayers();
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendActionBar(Component.text(actionbarTimer.getTimer()));
            }
        }, 0, 20);
    }
}
