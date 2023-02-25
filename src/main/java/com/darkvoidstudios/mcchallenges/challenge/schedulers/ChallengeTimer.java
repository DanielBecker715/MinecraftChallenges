package com.darkvoidstudios.mcchallenges.challenge.schedulers;

import com.darkvoidstudios.mcchallenges.MinecraftPlugin;
import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.randomitem.RandomItem;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.Instant;

@Getter
@Setter
public class ChallengeTimer extends BukkitRunnable {

    private static final ChallengeTimer challengeTimer = new ChallengeTimer();
    public static ChallengeTimer getInstance() {
        return challengeTimer;
    }
    static final Challenge challenge = Challenge.getInstance();

    long currentDays;
    int currentHours;
    int currentMinutes;
    int currentSeconds;

    @Override
    public void run() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MinecraftPlugin.getPlugin(), () -> {
            if (challenge.isChallengeActive()) {
                if (challenge.isRandomItemChallengeActive()) {
                    switch (currentSeconds) {
                        case 0:
                        case 29:
                            RandomItem.distributeRandomItemsToAllPlayers();
                            break;
                    }
                }

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendActionBar(Component.text(getTimer()));
                }
            }
        }, 0, 20);
    }

    /**
     * @return Returns a colorized string with the already elapsed time
     */
    public String getTimer() {
        Instant timestamp = challenge.getChallengeStartTimestamp();
        if (timestamp != null) {
            Instant currentTime = Instant.now();
            Duration diff = Duration.between(challenge.getChallengeStartTimestamp(), currentTime);

            String currentTimer = "§e§l";
            if (diff.toDays() != 0) {
                currentTimer = currentTimer + diff.toDays() + "d ";
                currentDays = diff.toDays();
            }
            if (diff.toHoursPart() != 0) {
                currentTimer = currentTimer + diff.toHoursPart() + "h ";
                currentHours = diff.toHoursPart();
            }
            if (diff.toMinutesPart() != 0) {
                currentTimer = currentTimer + diff.toMinutesPart() + "m ";
                currentMinutes = diff.toMinutesPart();
            }
            currentTimer = currentTimer + diff.toSecondsPart() + "s ";
            currentSeconds = diff.toSecondsPart();
            return currentTimer;
        }
        return null;
    }
    //TODO Stop the timer
}
