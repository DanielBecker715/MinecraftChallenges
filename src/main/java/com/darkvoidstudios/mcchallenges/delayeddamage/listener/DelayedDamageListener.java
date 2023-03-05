package com.darkvoidstudios.mcchallenges.delayeddamage.listener;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import com.darkvoidstudios.mcchallenges.delayeddamage.DelayedDamage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DelayedDamageListener implements Listener {
    final Challenge challenge = Challenge.getInstance();
    final DelayedDamage delayedDamage = DelayedDamage.getInstance();

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (challenge.isChallengeActive() && challenge.isDelayedDamageActive()) {
            if (event.getEntity() instanceof Player) {
                if (!delayedDamage.isStopListeningDamageEventActive()) {
                    delayedDamage.setDamageOfAllPlayers(delayedDamage.getDamageOfAllPlayers()+event.getDamage());
                    Player player = (Player) event.getEntity();
                    delayedDamage.setStopListeningDamageEventActive(true);
                    player.damage((float) 0.1);
                    delayedDamage.setStopListeningDamageEventActive(false);
                    event.setCancelled(true);
                }
            }
        }
    }
}
