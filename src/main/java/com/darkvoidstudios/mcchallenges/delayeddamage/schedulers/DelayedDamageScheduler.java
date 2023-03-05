package com.darkvoidstudios.mcchallenges.delayeddamage.schedulers;

import com.darkvoidstudios.mcchallenges.MinecraftPlugin;
import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.challenge.schedulers.ChallengeTimer;
import com.darkvoidstudios.mcchallenges.delayeddamage.DelayedDamage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
@Setter
public class DelayedDamageScheduler extends BukkitRunnable {

    static final Challenge challenge = Challenge.getInstance();
    static final ChallengeTimer challengeTimer = ChallengeTimer.getInstance();
    static final DelayedDamage delayedDamage = DelayedDamage.getInstance();



    @Override
    public void run() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MinecraftPlugin.getPlugin(), () -> {
            boolean isTimerDivisibleByDelay = challengeTimer.getCurrentMinutes() % delayedDamage.getDelayInMinutes() == 0;
            if (isTimerDivisibleByDelay) {
                delayedDamage.setStopListeningDamageEventActive(true);
                delayedDamage.distributeDamage();
                delayedDamage.setStopListeningDamageEventActive(false);
            }
        }, 0, 2400);
    }
}
