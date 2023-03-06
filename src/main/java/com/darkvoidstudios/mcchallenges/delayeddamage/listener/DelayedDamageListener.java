package com.darkvoidstudios.mcchallenges.delayeddamage.listener;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.delayeddamage.DelayedDamageChallenge;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DelayedDamageListener implements Listener {
    final Challenge challenge = Challenge.getInstance();
    final DelayedDamageChallenge delayedDamageChallenge = DelayedDamageChallenge.getInstance();

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (challenge.isChallengeActive() && challenge.isDelayedDamageActive()) {
            if (event.getEntity() instanceof Player) {
                if (!delayedDamageChallenge.isStopListeningDamageEventActive()) {
                    delayedDamageChallenge.setDamageOfAllPlayers(delayedDamageChallenge.getDamageOfAllPlayers()+event.getDamage());
                    Player player = (Player) event.getEntity();
                    delayedDamageChallenge.setStopListeningDamageEventActive(true);
                    player.damage((float) 0.1);
                    delayedDamageChallenge.setStopListeningDamageEventActive(false);
                    event.setCancelled(true);
                }
            }
        }
    }
}
