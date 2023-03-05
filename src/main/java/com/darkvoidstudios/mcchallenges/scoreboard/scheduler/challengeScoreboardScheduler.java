package com.darkvoidstudios.mcchallenges.scoreboard.scheduler;

import com.darkvoidstudios.mcchallenges.MinecraftPlugin;
import com.darkvoidstudios.mcchallenges.scoreboard.ChallengeScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class challengeScoreboardScheduler extends BukkitRunnable {

    final ChallengeScoreboard challengeScoreboard = ChallengeScoreboard.getInstance();

    @Override
    public void run() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MinecraftPlugin.getPlugin(), challengeScoreboard::updateScoreboard, 0, 20);
    }
}
