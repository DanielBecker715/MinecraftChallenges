package com.darkvoidstudios.mcchallenges.delayeddamage.schedulers;

import com.darkvoidstudios.mcchallenges.MinecraftPlugin;
import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.challenge.schedulers.ChallengeTimer;
import com.darkvoidstudios.mcchallenges.delayeddamage.DelayedDamageChallenge;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
@Setter
public class DelayedDamageScheduler extends BukkitRunnable {

    static final Challenge challenge = Challenge.getInstance();
    static final ChallengeTimer challengeTimer = ChallengeTimer.getInstance();
    static final DelayedDamageChallenge DELAYED_DAMAGE_CHALLENGE = DelayedDamageChallenge.getInstance();



    @Override
    public void run() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MinecraftPlugin.getPlugin(), () -> {
            boolean isTimerDivisibleByDelay = challengeTimer.getCurrentMinutes() % DELAYED_DAMAGE_CHALLENGE.getDelayInMinutes() == 0;
            if (isTimerDivisibleByDelay) {
                DELAYED_DAMAGE_CHALLENGE.setStopListeningDamageEventActive(true);
                DELAYED_DAMAGE_CHALLENGE.distributeDamage();
                DELAYED_DAMAGE_CHALLENGE.setStopListeningDamageEventActive(false);
            }
        }, 0, 2400);
    }
}
