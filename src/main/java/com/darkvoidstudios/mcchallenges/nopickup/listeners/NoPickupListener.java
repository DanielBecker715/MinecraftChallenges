package com.darkvoidstudios.mcchallenges.nopickup.listeners;

import com.darkvoidstudios.mcchallenges.challenge.models.Challenge;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class NoPickupListener implements Listener {

    final Challenge challenge = Challenge.getInstance();

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (challenge.isChallengeActive() && challenge.isNoPickupChallengeActive()) {
            if (event.getEntity() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }
}
