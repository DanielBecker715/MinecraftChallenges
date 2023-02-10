package com.darkvoidstudios.mcchallenges.challenge.schedulers;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.Instant;

public class ActionbarTimer extends BukkitRunnable {

    private final Plugin plugin;
    private Challenge challenge;

    public ActionbarTimer(Plugin plugin, Challenge challenge) {
        this.plugin = plugin;
        this.challenge = challenge;
    }

    @Override
    public void run() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (challenge.isChallengeActive()) {
                Instant currentTime = Instant.now();
                Duration diff = Duration.between(challenge.getTimerStartTimestamp(), currentTime);

                String message = "§e§l";
                if (diff.toDays() != 0) {
                    message = message + diff.toDays() + "d ";
                }
                if (diff.toHoursPart() != 0) {
                    message = message + diff.toHoursPart() + "h ";
                }
                if (diff.toMinutesPart() != 0) {
                    message = message + diff.toMinutesPart() + "m ";
                }
                message = message + diff.toSecondsPart() + "s ";

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendActionBar(Component.text(message));
                }
            }
        }, 0, 20);
    }
}