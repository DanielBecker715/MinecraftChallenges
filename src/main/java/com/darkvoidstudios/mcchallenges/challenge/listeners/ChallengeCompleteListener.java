package com.darkvoidstudios.mcchallenges.challenge.listeners;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class ChallengeCompleteListener implements Listener {
    final Challenge challenge = Challenge.getInstance();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (challenge.isChallengeActive()) {
            if (event.getEntity().getLastDamageCause() instanceof Player) {
                if (event.getEntityType() == EntityType.ENDER_DRAGON && event.getEntity().getWorld().getEnvironment().equals(World.Environment.THE_END)) {
                    challenge.challengeCompleted();
                }
            }
        }
    }
}
